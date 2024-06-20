package org.epam.gymCrmHiber.controller;

import org.epam.gymCrmHiber.controller.impl.TrainerControllerImpl;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.service.TrainerService;
import org.epam.gymCrmHiber.util.PasswordChangeRequest;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerControllerImplTest {

    private TrainerService trainerService;
    private TrainerControllerImpl trainerController;

    @BeforeEach
    void setUp() {
        trainerService = mock(TrainerService.class);
        trainerController = new TrainerControllerImpl(trainerService);
    }

    @Test
    void saveTrainer_Success() {
        Trainer trainer = new Trainer();
        ResponseEntity<String> response = trainerController.saveTrainer(trainer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainerService, times(1)).saveTrainer(trainer);
    }

    @Test
    void getTrainerByUsername_ExistingTrainer_Success() {
        UserCredentials credentials = new UserCredentials("username", "password");
        Trainer trainer = new Trainer();
        when(trainerService.getTrainerByUsername("username", "password")).thenReturn(Optional.of(trainer));

        ResponseEntity<Trainer> response = trainerController.getTrainerByUsername(credentials);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainer, response.getBody());
    }

    @Test
    void updateTrainer_Success() {
        Trainer trainer = new Trainer();
        UserCredentials credentials = new UserCredentials("username", "password");
        ResponseEntity<String> response = trainerController.updateTrainer(trainer, credentials);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainerService, times(1)).updateTrainer(trainer, "username", "password");
    }

    @Test
    void changeTrainerPassword_Success() {
        PasswordChangeRequest request = new PasswordChangeRequest("username", "oldPassword", "newPassword");
        ResponseEntity<String> response = trainerController.changeTrainerPassword(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainerService, times(1)).changeTrainersPassword("username", "oldPassword", "newPassword");
    }

    @Test
    void activateTrainer_Success() {
        UserCredentials credentials = new UserCredentials("username", "password");
        ResponseEntity<String> response = trainerController.activateTrainer(credentials);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainerService, times(1)).activateTrainer("username", "password");
    }

    @Test
    void deactivateTrainer_Success() {
        UserCredentials credentials = new UserCredentials("username", "password");
        ResponseEntity<String> response = trainerController.deactivateTrainer(credentials);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainerService, times(1)).deactivateTrainer("username", "password");
    }

    @Test
    void getUnassignedTrainers_Success() {
        UserCredentials credentials = new UserCredentials("username", "password");
        when(trainerService.getUnassignedTrainers("username", "password", "traineeUsername"))
                .thenReturn(Optional.of(Collections.singletonList(new Trainer())));

        ResponseEntity<List<Trainer>> response = trainerController.getUnassignedTrainers(credentials, "traineeUsername");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
