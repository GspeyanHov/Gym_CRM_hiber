package org.epam.gymCrmHiber.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.repository.TrainingRepository;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.entity.User;
import org.epam.gymCrmHiber.service.TrainingService;
import org.epam.gymCrmHiber.service.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final Logger logger = Logger.getLogger(TrainingServiceImpl.class.getName());
    private final TrainingRepository trainingRepository;
    private final UserDetailsService<User> userDetailsService;

    @Override
    public void createTraining(Training training, String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("Authentication failed for user: " + username + " permission denied");
        }
        trainingRepository.create(training);
        logger.info("Created training: " + training);
    }

    @Override
    public Optional<List<Training>> getTrainingByType(TrainingType type, String username, String password) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("Authentication failed for user: " + username + " permission denied");
        }
        logger.info("Got trainings by type: " + type.getType().name());
        return trainingRepository.selectByType(type);
    }

    @Override
    public Optional<List<Training>> getTraineeTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("Authentication failed for user: " + username + " permission denied");
        }
        logger.info("Got trainings by trainee: " + username);
        return trainingRepository.getTraineeTrainings(username, fromDate, toDate, trainerName, trainingType);
    }

    @Override
    public Optional<List<Training>> getTrainerTrainings(String username, String password, LocalDateTime fromDate, LocalDateTime toDate, String traineeName) {
        if (!userDetailsService.authenticate(username, password)) {
            throw new SecurityException("Authentication failed for user: " + username + " permission denied");
        }
        logger.info("Got trainings by trainer: " + username);
        return trainingRepository.getTrainerTrainings(username, fromDate, toDate, traineeName);
    }
}
