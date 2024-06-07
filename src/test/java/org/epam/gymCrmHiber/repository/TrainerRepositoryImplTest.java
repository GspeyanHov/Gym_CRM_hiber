package org.epam.gymCrmHiber.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.repository.impl.TrainerRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class TrainerRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Trainer> query;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private TrainerRepositoryImpl trainerRepository;

    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void testCreate() {
        Trainer trainer = new Trainer();
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.save(trainer)).thenReturn("testUser");
        doNothing().when(transaction).commit();
        trainerRepository.create(trainer);

        verify(sessionFactory).openSession();
        verify(session).beginTransaction();
        verify(session).save(trainer);
        verify(transaction).commit();
    }

    @Test
    void testUpdate() {
        Trainer trainer = new Trainer();
        Transaction mockTransaction = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(mockTransaction);
        assertDoesNotThrow(() -> trainerRepository.update(trainer));
        verify(session).update(trainer);
        verify(mockTransaction).commit();
    }

    @Test
    void testSelectByUsername() {
        String username = "testUsername";
        Session session = mock(Session.class);
        Query query = mock(Query.class);
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(trainers);
        Optional<Trainer> result = trainerRepository.selectByUsername(username);
        assertNotNull(result);
    }

    @Test
    void testChangeTrainersPassword() {
        // Arrange
        String username = "testUsername";
        String newPassword = "newPassword";
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        Trainer trainer = new Trainer();
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainer);
        trainerRepository.changeTrainersPassword(username, newPassword);
        assertEquals(newPassword, trainer.getPassword());
    }

    @Test
    void testActivateTrainer() {
        String username = "testUsername";
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        Trainer trainer = new Trainer();
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainer);
        trainerRepository.activateTrainer(username);
        assertTrue(trainer.isActive());

    }

    @Test
    void testDeactivateTrainer() {
        // Arrange
        String username = "testUsername";
        Query query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        Trainer trainer = new Trainer();
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainer);
        trainerRepository.deactivateTrainer(username);
        assertFalse(trainer.isActive());
    }

    @Test
    void testGetUnassignedTrainers() {
        String traineeUsername = "testTrainee";
        String trainerUsername1 = "trainer1";
        String trainerUsername2 = "trainer2";

        Trainer trainer1 = new Trainer();
        trainer1.setUsername(trainerUsername1);

        Trainer trainer2 = new Trainer();
        trainer2.setUsername(trainerUsername2);

        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainer1, trainer2));
        Optional<List<Trainer>> unassignedTrainersOptional = trainerRepository.getUnassignedTrainers(traineeUsername);

        assertTrue(unassignedTrainersOptional.isPresent());
        List<Trainer> unassignedTrainers = unassignedTrainersOptional.get();
        assertEquals(2, unassignedTrainers.size());
        assertTrue(unassignedTrainers.stream().noneMatch(trainer ->
                trainer.getUsername().equals(traineeUsername)));
    }
}
