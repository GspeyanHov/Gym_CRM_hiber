package org.epam.gymCrmHiber.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.controller.impl.TraineeControllerImpl;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.service.TraineeService;
import org.epam.gymCrmHiber.util.PasswordChangeRequest;
import org.epam.gymCrmHiber.util.UserCredentials;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class TraineeControllerImplTest {

    @Mock
    private TraineeService traineeService;

    private TraineeControllerImpl traineeController;


    @BeforeEach
    @Deprecated
    void setUp() {
        MockitoAnnotations.initMocks(this);
        traineeController = new TraineeControllerImpl(traineeService);
    }

    @Test
    void testSaveTrainee() {
        // Arrange
        Trainee trainee = new Trainee();

        // Act
        ResponseEntity<String> response = traineeController.saveTrainee(trainee);

        // Assert
        assertEquals("Trainee saved successfully", response.getBody());
    }

    @Test
    void testGetAllTrainees() {
        // Arrange
        when(traineeService.getAllTrainees(any(), any())).thenReturn(Optional.of(Collections.emptyList()));
        UserCredentials credentials = new UserCredentials("username", "password");

        // Act
        ResponseEntity<List<Trainee>> response = traineeController.getAllTrainees(credentials);

        // Assert
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void testGetTraineeByUsername() {
        // Arrange
        when(traineeService.getTraineeByUsername(any(), any())).thenReturn(Optional.of(new Trainee()));
        UserCredentials credentials = new UserCredentials("username", "password");

        // Act
        ResponseEntity<Trainee> response = traineeController.getTraineeByUsername(credentials);

        // Assert
        assertEquals(new Trainee(), response.getBody());
    }

    @Test
    void testUpdateTrainee() {
        // Arrange
        Trainee trainee = new Trainee();
        UserCredentials credentials = new UserCredentials("username", "password");

        // Act
        ResponseEntity<String> response = traineeController.updateTrainee(trainee, credentials);

        // Assert
        assertEquals("Trainee updated successfully", response.getBody());
    }

    @Test
    void testDeleteTrainee() {
        // Arrange
        UserCredentials credentials = new UserCredentials("username", "password");

        // Act
        ResponseEntity<String> response = traineeController.deleteTrainee(credentials);

        // Assert
        assertEquals("Trainee deleted successfully", response.getBody());
    }

    @Test
    void testChangeTraineesPassword() {
        // Arrange
        PasswordChangeRequest request = new PasswordChangeRequest("username", "oldPassword", "newPassword");

        // Act
        ResponseEntity<String> response = traineeController.changeTraineesPassword(request);

        // Assert
        assertEquals("Password changed successfully", response.getBody());
    }

    @Test
    void testActivateTrainee() {
        // Arrange
        UserCredentials credentials = new UserCredentials("username", "password");

        // Act
        ResponseEntity<String> response = traineeController.activateTrainee(credentials);

        // Assert
        assertEquals("Trainee activated successfully", response.getBody());
    }

    @Test
    void testDeactivateTrainee() {
        // Arrange
        UserCredentials credentials = new UserCredentials("username", "password");

        // Act
        ResponseEntity<String> response = traineeController.deactivateTrainee(credentials);

        // Assert
        assertEquals("Trainee deactivated successfully", response.getBody());
    }
}
