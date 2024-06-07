package org.epam.gymCrmHiber.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;

public interface TrainingRepository {

    void create(Training training);

    Optional<List<Training>> selectByType(TrainingType type);

    Optional<List<Training>> getTraineeTrainings(String username, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType);

    Optional<List<Training>> getTrainerTrainings(String username, LocalDateTime fromDate, LocalDateTime toDate, String traineeName);
}
