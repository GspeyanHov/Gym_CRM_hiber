package org.epam.gymCrmHiber.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.repository.TraineeRepository;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.service.TraineeService;
import org.epam.gymCrmHiber.service.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final Logger logger = Logger.getLogger(TraineeServiceImpl.class.getName());
    private final TraineeRepository traineeRepository;
    private final UserDetailsService<Trainee> userDetailsService;

    @Override
    public void saveTrainee(Trainee trainee) {
        traineeRepository.create(trainee);
        logger.info("Trainee " + trainee + " saved");
    }

    @Override
    public Optional<List<Trainee>> getAllTrainees(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        logger.info("Trainee list loaded");
        return traineeRepository.listAll();
    }

    @Override
    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        logger.info("Trainee " + username + " loaded");
        return traineeRepository.selectByUsername(username);
    }

    @Override
    public void updateTrainee(Trainee trainee, String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        traineeRepository.update(trainee);
        logger.info("Trainee " + trainee + " updated");
    }

    @Override
    public void deleteTrainee(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        traineeRepository.delete(username);
        logger.info("Trainee " + username + " deleted");
    }

    @Override
    public void changeTraineesPassword(String username, String oldPassword, String newPassword) {
        if (!userDetailsService.authenticate(username, oldPassword)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        traineeRepository.changeTraineesPassword(username, newPassword);
        logger.info("Trainee " + username + " password changed");
    }

    @Override
    public void activateTrainee(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        traineeRepository.activateTrainee(username);
        logger.info("Trainee " + username + " activated");
    }

    @Override
    public void deactivateTrainee(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        traineeRepository.deactivateTrainee(username);
        logger.info("Trainee " + username + " deactivated");
    }
}
