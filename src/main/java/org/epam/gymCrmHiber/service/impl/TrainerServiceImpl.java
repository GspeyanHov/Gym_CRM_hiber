package org.epam.gymCrmHiber.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.repository.TrainerRepository;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.service.TrainerService;
import org.epam.gymCrmHiber.service.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final Logger logger = Logger.getLogger(TrainerServiceImpl.class.getName());
    private final TrainerRepository trainerRepository;
    private final UserDetailsService<Trainer> userDetailsService;

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerRepository.create(trainer);
        logger.info("Created Trainer " + trainer.getFirstname() + " " + trainer.getLastname());
    }

    @Override
    public void updateTrainer(Trainer trainer, String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("Invalid username or password");
        }
        trainerRepository.update(trainer);
        logger.info("Updated Trainer " + trainer.getFirstname() + " " + trainer.getLastname());
    }

    @Override
    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("Invalid username or password");
        }
        logger.info("Get trainer by id " + username);
        return trainerRepository.selectByUsername(username);
    }

    @Override
    public void changeTrainersPassword(String username, String oldPassword, String newPassword) {
        if (!userDetailsService.authenticate(username, oldPassword)) {
            throw new SecurityException("Invalid username or password");
        }
        trainerRepository.changeTrainersPassword(username, newPassword);
        logger.info("Change password for trainer " + username);
    }

    @Override
    public void activateTrainer(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        trainerRepository.activateTrainer(username);
        logger.info("Trainee " + username + " activated");
    }

    @Override
    public void deactivateTrainer(String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        trainerRepository.deactivateTrainer(username);
        logger.info("Trainee " + username + " deactivated");
    }

    @Override
    public Optional<List<Trainer>> getUnassignedTrainers(String username, String password, String traineeUsername) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("User " + username + " not authenticated, permission denied!");
        }
        logger.info("Get unassigned trainers for trainee " + traineeUsername);
        return trainerRepository.getUnassignedTrainers(traineeUsername);
    }
}
