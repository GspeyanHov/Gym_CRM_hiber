package org.epam.gymCrmHiber.controller.impl;

import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.controller.TrainerController;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.service.TrainerService;
import org.epam.gymCrmHiber.util.PasswordChangeRequest;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainerControllerImpl implements TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerControllerImpl(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public ResponseEntity<String> saveTrainer(Trainer trainer) {
        trainerService.saveTrainer(trainer);
        return new ResponseEntity<>("Trainer saved successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Trainer> getTrainerByUsername(UserCredentials credentials) {
        Optional<Trainer> trainer = trainerService.getTrainerByUsername(credentials.getUsername(), credentials.getPassword());
        return trainer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<String> updateTrainer(Trainer trainer, UserCredentials credentials) {
        trainerService.updateTrainer(trainer, credentials.getUsername(), credentials.getPassword());
        return new ResponseEntity<>("Trainer updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changeTrainerPassword(PasswordChangeRequest request) {
        trainerService.changeTrainersPassword(request.getUsername(), request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> activateTrainer(UserCredentials credentials) {
        trainerService.activateTrainer(credentials.getUsername(), credentials.getPassword());
        return new ResponseEntity<>("Trainer activated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deactivateTrainer(UserCredentials credentials) {
        trainerService.deactivateTrainer(credentials.getUsername(), credentials.getPassword());
        return new ResponseEntity<>("Trainer deactivated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Trainer>> getUnassignedTrainers(UserCredentials credentials, @RequestParam String traineeUsername) {
        Optional<List<Trainer>> unassignedTrainers = trainerService.getUnassignedTrainers(credentials.getUsername(), credentials.getPassword(), traineeUsername);
        return unassignedTrainers
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
