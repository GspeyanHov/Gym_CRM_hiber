package org.epam.gymCrmHiber.service;

import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainee;

public interface TraineeService {

    void saveTrainee(Trainee trainee);

    Optional<List<Trainee>> getAllTrainees(String userName, String password);

    Optional<Trainee> getTraineeByUsername(String username, String password);

    void updateTrainee(Trainee trainee, String username, String password);

    void deleteTrainee(String username, String password);

    void changeTraineesPassword(String username, String oldPassword, String newPassword);

    void activateTrainee(String username, String password);

    void deactivateTrainee(String username, String password);
}
