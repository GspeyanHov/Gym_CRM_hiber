package org.epam.gymCrmHiber.controller.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.controller.TraineeController;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.service.TraineeService;
import org.epam.gymCrmHiber.util.PasswordChangeRequest;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TraineeControllerImpl implements TraineeController {

    private final TraineeService traineeService;

    @Override
    public ResponseEntity<String> saveTrainee(Trainee trainee) {
        traineeService.saveTrainee(trainee);
        return ResponseEntity.ok("Trainee saved successfully");
    }

    @Override
    public ResponseEntity<List<Trainee>> getAllTrainees(UserCredentials credentials) {
        Optional<List<Trainee>> trainees = traineeService.getAllTrainees(credentials.getUsername(), credentials.getPassword());
        return trainees.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<Trainee> getTraineeByUsername(UserCredentials credentials) {
        Optional<Trainee> trainee = traineeService.getTraineeByUsername(credentials.getUsername(), credentials.getPassword());
        return trainee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<String> updateTrainee(Trainee trainee, UserCredentials credentials) {
        traineeService.updateTrainee(trainee, credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok("Trainee updated successfully");
    }

    @Override
    public ResponseEntity<String> deleteTrainee(UserCredentials credentials) {
        traineeService.deleteTrainee(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok("Trainee deleted successfully");
    }

    @Override
    public ResponseEntity<String> changeTraineesPassword(PasswordChangeRequest request) {
        traineeService.changeTraineesPassword(request.getUsername(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }

    @Override
    public ResponseEntity<String> activateTrainee(UserCredentials credentials) {
        traineeService.activateTrainee(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok("Trainee activated successfully");
    }

    @Override
    public ResponseEntity<String> deactivateTrainee(UserCredentials credentials) {
        traineeService.deactivateTrainee(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok("Trainee deactivated successfully");
    }
}
