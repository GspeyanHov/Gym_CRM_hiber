package org.epam.gymCrmHiber.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.repository.impl.TraineeRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class TraineeRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private TraineeRepositoryImpl traineeRepository;

    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testCreateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstname("John");
        trainee.setLastname("Doe");
        traineeRepository.create(trainee);
        verify(session).save(trainee);
        verify(transaction).commit();
    }

    @Test
    public void testUpdateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setUsername("testUsername");
        traineeRepository.update(trainee);
        verify(session).update(trainee);
        verify(transaction).commit();
    }

    @Test
    public void testDeleteTrainee() {
        String username = "testUsername";
        traineeRepository.delete(username);
        verify(session).delete(any());
        verify(transaction).commit();
    }

    @Test
    public void testSelectByUsername() {
        String username = "testUsername";
        Trainee trainee = new Trainee();
        trainee.setUsername(username);
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);
        Optional<Trainee> result = traineeRepository.selectByUsername(username);
        assertEquals(trainee, result.orElse(null));
    }


    @Test
    public void testListAll() {
        List<Trainee> trainees = Arrays.asList(new Trainee(), new Trainee());
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.list()).thenReturn(trainees);
        Optional<List<Trainee>> result = traineeRepository.listAll();
        assertEquals(trainees, result.orElse(null));
    }

    @Test
    public void testChangeTraineesPassword() {
        String username = "testUsername";
        String newPassword = "newPassword";
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        Trainee trainee = new Trainee();
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);
        traineeRepository.changeTraineesPassword(username, newPassword);
        assertEquals(newPassword, trainee.getPassword());
    }

    @Test
    public void testActivateTrainee() {
        String username = "testUsername";
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        Trainee trainee = new Trainee();
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);
        traineeRepository.activateTrainee(username);
        assertTrue(trainee.isActive());
    }

    @Test
    public void testDeactivateTrainee() {
        String username = "testUsername";
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        Trainee trainee = new Trainee();
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);
        traineeRepository.deactivateTrainee(username);
        assertFalse(trainee.isActive());
    }

    @Test
    void testUpdateTraineeTrainers() {
        String username = "testUser";
        List<Trainer> trainers = List.of(new Trainer(), new Trainer());
        Trainee trainee = mock(Trainee.class);
        when(trainee.getUsername()).thenReturn(username);
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        Query<Trainee> query = mock(Query.class);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);
        traineeRepository = new TraineeRepositoryImpl(sessionFactory);
        traineeRepository.updateTraineeTrainers(username, trainers);

        verify(sessionFactory).openSession();
        verify(session).beginTransaction();
        verify(session).createQuery("FROM Trainee WHERE username = :username", Trainee.class);
        verify(query).setParameter("username", username);
        verify(query).uniqueResult();
        verify(trainee).setTrainers(new HashSet<>(trainers));
        verify(session).update(trainee);
        verify(transaction).commit();
        verify(transaction, never()).rollback();
    }
}


