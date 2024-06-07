package org.epam.gymCrmHiber.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;

public interface TrainingService {

    void createTraining(Training training, String username, String password);

    Optional<List<Training>> getTrainingByType(TrainingType type, String username, String password);

    Optional<List<Training>> getTraineeTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType);

    Optional<List<Training>> getTrainerTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String traineeName);
}
