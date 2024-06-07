package org.epam.gymCrmHiber.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.entity.Type;
import org.epam.gymCrmHiber.repository.impl.TrainingRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

class TrainingRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Training> query;

    @InjectMocks
    private TrainingRepositoryImpl trainingRepository;

    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        Training training = new Training();
        Transaction mockTransaction = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(mockTransaction);
        when(session.save(training)).thenReturn("testUser");
        doNothing().when(mockTransaction).commit();
        trainingRepository.create(training);

        verify(sessionFactory).openSession();
        verify(session).beginTransaction();
        verify(session).save(training);
        verify(mockTransaction).commit();
    }

    @Test
    void testSelectByType() {
        TrainingType trainingType = new TrainingType();
        trainingType.setType(Type.AEROBICS);
        Training training1 = new Training();
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Training.class))).thenReturn(query);
        when(query.setParameter(anyString(), eq(Type.AEROBICS.name()))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(training1));

        Optional<List<Training>> result = trainingRepository.selectByType(trainingType);

        assertEquals(1, result.orElseThrow().size());
    }

    @Test
    void testGetTraineeTrainings() {
        String username = "testUsername";
        LocalDateTime fromDate = LocalDateTime.now().minusDays(7);
        LocalDateTime toDate = LocalDateTime.now();
        String trainerName = "testTrainer";

        Type type = Type.AEROBICS;
        TrainingType trainingType = new TrainingType();
        trainingType.setType(type);

        Training training1 = new Training();
        Training training2 = new Training();

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Training.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(training1, training2));

        Optional<List<Training>> result = trainingRepository.getTraineeTrainings(username, fromDate, toDate, trainerName, trainingType);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
    }

    @Test
    void testGetTrainerTrainings() {
        String username = "testUsername";
        LocalDateTime fromDate = LocalDateTime.now().minusDays(7);
        LocalDateTime toDate = LocalDateTime.now();
        String traineeName = "testTrainee";
        Training training1 = new Training();
        Training training2 = new Training();

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Training.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(training1, training2));

        Optional<List<Training>> result = trainingRepository.getTrainerTrainings(username, fromDate, toDate, traineeName);

        assertEquals(2, result.orElseThrow().size());
    }
}
