package org.epam.gymCrmHiber.endpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.service.TraineeService;
import org.epam.gymCrmHiber.service.TrainerService;
import org.epam.gymCrmHiber.service.TrainingService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class EndpointImplTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private EndpointImpl endpoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTrainee_ShouldCallService() {
        Trainee trainee = new Trainee();
        endpoint.saveTrainee(trainee);
        verify(traineeService, times(1)).saveTrainee(trainee);
    }

    @Test
    void getAllTrainees_ShouldReturnTrainees() {
        String username = "user";
        String password = "pass";
        List<Trainee> trainees = List.of(new Trainee());
        when(traineeService.getAllTrainees(username, password)).thenReturn(Optional.of(trainees));

        Optional<List<Trainee>> result = endpoint.getAllTrainees(username, password);

        verify(traineeService, times(1)).getAllTrainees(username, password);
        assertTrue(result.isPresent());
        assertEquals(trainees, result.get());
    }

    @Test
    void getTraineeByUsername_ShouldReturnTrainee() {
        String username = "user";
        String password = "pass";
        Trainee trainee = new Trainee();
        when(traineeService.getTraineeByUsername(username, password)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = endpoint.getTraineeByUsername(username, password);

        verify(traineeService, times(1)).getTraineeByUsername(username, password);
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void updateTrainee_ShouldCallService() {
        Trainee trainee = new Trainee();
        String username = "user";
        String password = "pass";
        endpoint.updateTrainee(trainee, username, password);
        verify(traineeService, times(1)).updateTrainee(trainee, username, password);
    }

    @Test
    void deleteTrainee_ShouldCallService() {
        String username = "user";
        String password = "pass";
        endpoint.deleteTrainee(username, password);
        verify(traineeService, times(1)).deleteTrainee(username, password);
    }

    @Test
    void changeTraineesPassword_ShouldCallService() {
        String username = "user";
        String oldPassword = "old";
        String newPassword = "new";
        endpoint.changeTraineesPassword(username, oldPassword, newPassword);
        verify(traineeService, times(1)).changeTraineesPassword(username, oldPassword, newPassword);
    }

    @Test
    void activateTrainee_ShouldCallService() {
        String username = "user";
        String password = "pass";
        endpoint.activateTrainee(username, password);
        verify(traineeService, times(1)).activateTrainee(username, password);
    }

    @Test
    void deactivateTrainee_ShouldCallService() {
        String username = "user";
        String password = "pass";
        endpoint.deactivateTrainee(username, password);
        verify(traineeService, times(1)).deactivateTrainee(username, password);
    }

    @Test
    void saveTrainer_ShouldCallService() {
        Trainer trainer = new Trainer();
        endpoint.saveTrainer(trainer);
        verify(trainerService, times(1)).saveTrainer(trainer);
    }

    @Test
    void updateTrainer_ShouldCallService() {
        Trainer trainer = new Trainer();
        String username = "user";
        String password = "pass";
        endpoint.updateTrainer(trainer, username, password);
        verify(trainerService, times(1)).updateTrainer(trainer, username, password);
    }

    @Test
    void getTrainerByUsername_ShouldReturnTrainer() {
        String username = "user";
        String password = "pass";
        Trainer trainer = new Trainer();
        when(trainerService.getTrainerByUsername(username, password)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = endpoint.getTrainerByUsername(username, password);

        verify(trainerService, times(1)).getTrainerByUsername(username, password);
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void changeTrainersPassword_ShouldCallService() {
        String username = "user";
        String oldPassword = "old";
        String newPassword = "new";
        endpoint.changeTrainersPassword(username, oldPassword, newPassword);
        verify(trainerService, times(1)).changeTrainersPassword(username, oldPassword, newPassword);
    }

    @Test
    void activateTrainer_ShouldCallService() {
        String username = "user";
        String password = "pass";
        endpoint.activateTrainer(username, password);
        verify(trainerService, times(1)).activateTrainer(username, password);
    }

    @Test
    void deactivateTrainer_ShouldCallService() {
        String username = "user";
        String password = "pass";
        endpoint.deactivateTrainer(username, password);
        verify(trainerService, times(1)).deactivateTrainer(username, password);
    }

    @Test
    void getUnassignedTrainers_ShouldReturnTrainers() {
        String username = "user";
        String password = "pass";
        String traineeUsername = "trainee";
        List<Trainer> trainers = List.of(new Trainer());
        when(trainerService.getUnassignedTrainers(username, password, traineeUsername)).thenReturn(Optional.of(trainers));

        Optional<List<Trainer>> result = endpoint.getUnassignedTrainers(username, password, traineeUsername);

        verify(trainerService, times(1)).getUnassignedTrainers(username, password, traineeUsername);
        assertTrue(result.isPresent());
        assertEquals(trainers, result.get());
    }

    @Test
    void createTraining_ShouldCallService() {
        Training training = new Training();
        String username = "user";
        String password = "pass";
        endpoint.createTraining(training, username, password);
        verify(trainingService, times(1)).createTraining(training, username, password);
    }

    @Test
    void getTrainingByType_ShouldReturnTrainings() {
        TrainingType type = new TrainingType();
        String username = "user";
        String password = "pass";
        List<Training> trainings = List.of(new Training());
        when(trainingService.getTrainingByType(type, username, password)).thenReturn(Optional.of(trainings));

        Optional<List<Training>> result = endpoint.getTrainingByType(type, username, password);

        verify(trainingService, times(1)).getTrainingByType(type, username, password);
        assertTrue(result.isPresent());
        assertEquals(trainings, result.get());
    }

    @Test
    void getTraineeTrainings_ShouldReturnTrainings() {
        String username = "user";
        String password = "pass";
        LocalDateTime fromDate = LocalDateTime.now();
        LocalDateTime toDate = LocalDateTime.now();
        String trainerName = "trainer";
        TrainingType trainingType = new TrainingType();
        List<Training> trainings = List.of(new Training());
        when(trainingService.getTraineeTrainings(username, password, fromDate, toDate, trainerName, trainingType)).thenReturn(Optional.of(trainings));

        Optional<List<Training>> result = endpoint.getTraineeTrainings(username, password, fromDate, toDate, trainerName, trainingType);

        verify(trainingService, times(1)).getTraineeTrainings(username, password, fromDate, toDate, trainerName, trainingType);
        assertTrue(result.isPresent());
        assertEquals(trainings, result.get());
    }

    @Test
    void getTrainerTrainings_ShouldReturnTrainings() {
        String username = "user";
        String password = "pass";
        LocalDateTime fromDate = LocalDateTime.now();
        LocalDateTime toDate = LocalDateTime.now();
        String traineeName = "trainee";
        List<Training> trainings = List.of(new Training());
        when(trainingService.getTrainerTrainings(username, password, fromDate, toDate, traineeName)).thenReturn(Optional.of(trainings));

        Optional<List<Training>> result = endpoint.getTrainerTrainings(username, password, fromDate, toDate, traineeName);

        verify(trainingService, times(1)).getTrainerTrainings(username, password, fromDate, toDate, traineeName);
        assertTrue(result.isPresent());
        assertEquals(trainings, result.get());
    }
}
