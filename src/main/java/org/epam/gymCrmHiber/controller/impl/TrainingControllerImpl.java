package org.epam.gymCrmHiber.controller.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.controller.TrainingController;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.service.TrainingService;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    @Override
    public ResponseEntity<String> createTraining(Training training, UserCredentials userCredentials) {
        trainingService.createTraining(training, userCredentials.getUsername(), userCredentials.getPassword());
        return new ResponseEntity<>("Training created successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Training>> getTrainingByType(TrainingType type, UserCredentials userCredentials) {
        Optional<List<Training>> trainings = trainingService.getTrainingByType(type, userCredentials.getUsername(), userCredentials.getPassword());
        return trainings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Override
    public ResponseEntity<List<Training>> getTraineeTrainings(UserCredentials userCredentials, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType) {
        Optional<List<Training>> trainings = trainingService.getTraineeTrainings(userCredentials.getUsername(), userCredentials.getPassword(), fromDate, toDate, trainerName, trainingType);
        return trainings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Override
    public ResponseEntity<List<Training>> getTrainerTrainings(UserCredentials userCredentials, LocalDateTime fromDate, LocalDateTime toDate, String traineeName) {
        Optional<List<Training>> trainings = trainingService.getTrainerTrainings(userCredentials.getUsername(), userCredentials.getPassword(), fromDate, toDate, traineeName);
        return trainings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
