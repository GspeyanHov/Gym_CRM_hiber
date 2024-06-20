package org.epam.gymCrmHiber.controller;

import java.time.LocalDateTime;
import java.util.List;
import static org.epam.gymCrmHiber.Main.API_URL;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(API_URL + "/trainings")
public interface TrainingController {

    @PostMapping("/add")
    ResponseEntity<String> createTraining(
            @RequestBody Training training,
            @RequestBody UserCredentials credentials);

    @PostMapping("/by-type")
    ResponseEntity<List<Training>> getTrainingByType(
            @RequestBody TrainingType type,
            @RequestBody UserCredentials credentials);

    @PostMapping("/trainee-trainings")
    ResponseEntity<List<Training>> getTraineeTrainings(
            @RequestBody UserCredentials userCredentials,
            @RequestParam LocalDateTime fromDate,
            @RequestParam LocalDateTime toDate,
            @RequestParam String traineeName,
            @RequestBody TrainingType trainingType);

    @PostMapping("/trainer-trainings")
    ResponseEntity<List<Training>> getTrainerTrainings(
            @RequestBody UserCredentials userCredentials,
            @RequestParam LocalDateTime fromDate,
            @RequestParam LocalDateTime toDate,
            @RequestParam String trainerName);


}
