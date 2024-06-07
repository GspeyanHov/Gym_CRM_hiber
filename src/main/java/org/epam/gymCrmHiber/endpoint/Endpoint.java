package org.epam.gymCrmHiber.endpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;

public interface Endpoint {


    void saveTrainee(Trainee trainee);


    Optional<List<Trainee>> getAllTrainees(String userName, String password);


    Optional<Trainee> getTraineeByUsername(String username, String password);


    void updateTrainee(Trainee trainee, String username, String password);


    void deleteTrainee(String username, String password);


    void changeTraineesPassword(String username, String oldPassword, String newPassword);


    void activateTrainee(String username, String password);


    void deactivateTrainee(String username, String password);


    void saveTrainer(Trainer trainer);


    void updateTrainer(Trainer trainer, String username, String password);


    Optional<Trainer> getTrainerByUsername(String username, String password);


    void changeTrainersPassword(String username, String oldPassword, String newPassword);


    void activateTrainer(String username, String password);


    void deactivateTrainer(String username, String password);


    Optional<List<Trainer>> getUnassignedTrainers(String username, String password, String traineeUsername);


    public void createTraining(Training training, String username, String password);


    public Optional<List<Training>> getTrainingByType(TrainingType type, String username, String password);


    Optional<List<Training>> getTraineeTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType);


    Optional<List<Training>> getTrainerTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String traineeName);

}
