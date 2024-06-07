package org.epam.gymCrmHiber.repository;

import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainer;

public interface TrainerRepository {

    void create(Trainer trainer);

    void update(Trainer trainer);

    Optional<Trainer> selectByUsername(String username);

    void changeTrainersPassword(String username, String password);

    void activateTrainer(String username);

    void deactivateTrainer(String username);

    Optional<List<Trainer>> getUnassignedTrainers(String traineeUsername);
}
