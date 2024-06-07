package org.epam.gymCrmHiber.repository;

import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.entity.Trainer;

public interface TraineeRepository {

    void create(Trainee trainee);

    void update(Trainee trainee);

    void delete(String username);

    Optional<Trainee> selectByUsername(String username);

    Optional<List<Trainee>> listAll();

    void changeTraineesPassword(String username, String password);

    void activateTrainee(String username);

    void deactivateTrainee(String username);

    void updateTraineeTrainers(String traineeUsername, List<Trainer> trainers);
}
