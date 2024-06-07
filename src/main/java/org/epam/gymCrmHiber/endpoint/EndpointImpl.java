package org.epam.gymCrmHiber.endpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.service.TraineeService;
import org.epam.gymCrmHiber.service.TrainerService;
import org.epam.gymCrmHiber.service.TrainingService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EndpointImpl implements Endpoint {

    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final TrainerService trainerService;

    @Override
    public void saveTrainee(Trainee trainee) {
        traineeService.saveTrainee(trainee);
    }

    @Override
    public Optional<List<Trainee>> getAllTrainees(String userName, String password) {
        return traineeService.getAllTrainees(userName, password);
    }

    @Override
    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        return traineeService.getTraineeByUsername(username, password);
    }

    @Override
    public void updateTrainee(Trainee trainee, String username, String password) {
        traineeService.updateTrainee(trainee, username, password);
    }

    @Override
    public void deleteTrainee(String username, String password) {
        traineeService.deleteTrainee(username, password);
    }

    @Override
    public void changeTraineesPassword(String username, String oldPassword, String newPassword) {
        traineeService.changeTraineesPassword(username, oldPassword, newPassword);
    }

    @Override
    public void activateTrainee(String username, String password) {
        traineeService.activateTrainee(username, password);
    }

    @Override
    public void deactivateTrainee(String username, String password) {
        traineeService.deactivateTrainee(username, password);
    }

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerService.saveTrainer(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer, String username, String password) {
        trainerService.updateTrainer(trainer, username, password);
    }

    @Override
    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        return trainerService.getTrainerByUsername(username, password);
    }

    @Override
    public void changeTrainersPassword(String username, String oldPassword, String newPassword) {
        trainerService.changeTrainersPassword(username, oldPassword, newPassword);
    }

    @Override
    public void activateTrainer(String username, String password) {
        trainerService.activateTrainer(username, password);
    }

    @Override
    public void deactivateTrainer(String username, String password) {
        trainerService.deactivateTrainer(username, password);
    }

    @Override
    public Optional<List<Trainer>> getUnassignedTrainers(String username, String password, String traineeUsername) {
        return trainerService.getUnassignedTrainers(username, password, traineeUsername);
    }

    @Override
    public void createTraining(Training training, String username, String password) {
        trainingService.createTraining(training, username, password);
    }

    @Override
    public Optional<List<Training>> getTrainingByType(TrainingType type, String username, String password) {
        return trainingService.getTrainingByType(type, username, password);
    }

    @Override
    public Optional<List<Training>> getTraineeTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType) {
        return trainingService.getTraineeTrainings(username, password, fromDate, toDate, trainerName, trainingType);
    }

    @Override
    public Optional<List<Training>> getTrainerTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String traineeName) {
        return trainingService.getTrainerTrainings(username, password, fromDate, toDate, traineeName);
    }
}
