package org.epam.gymCrmHiber.service;

import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.repository.TraineeRepository;
import org.epam.gymCrmHiber.service.impl.TraineeServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserDetailsService<Trainee> userDetailsService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setUsername("testUser");
        trainee.setPassword("testPassword");
    }

    @Test
    void saveTrainee_ShouldCallRepositoryCreate() {
        traineeService.saveTrainee(trainee);
        verify(traineeRepository, times(1)).create(trainee);
    }

    @Test
    void getAllTrainees_ShouldReturnListOfTrainees_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        when(traineeRepository.listAll()).thenReturn(Optional.of(List.of(trainee)));

        Optional<List<Trainee>> result = traineeService.getAllTrainees("testUser", "testPassword");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(trainee, result.get().get(0));
    }

    @Test
    void getAllTrainees_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);

        assertThrows(SecurityException.class, () -> traineeService.getAllTrainees("testUser", "testPassword"));
    }

    @Test
    void getTraineeByUsername_ShouldReturnTrainee_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        when(traineeRepository.selectByUsername(anyString())).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.getTraineeByUsername("testUser", "testPassword");

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void getTraineeByUsername_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);

        assertThrows(SecurityException.class, () -> traineeService.getTraineeByUsername("testUser", "testPassword"));
    }

    @Test
    void updateTrainee_ShouldCallRepositoryUpdate_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        traineeService.updateTrainee(trainee, "testUser", "testPassword");

        verify(traineeRepository, times(1)).update(trainee);
    }

    @Test
    void updateTrainee_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> traineeService.updateTrainee(trainee, "testUser", "testPassword"));
    }

    @Test
    void deleteTrainee_ShouldCallRepositoryDelete_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        traineeService.deleteTrainee("testUser", "testPassword");

        verify(traineeRepository, times(1)).delete("testUser");
    }

    @Test
    void deleteTrainee_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> traineeService.deleteTrainee("testUser", "testPassword"));
    }

    @Test
    void changeTraineesPassword_ShouldCallRepositoryChangeTraineesPassword_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        traineeService.changeTraineesPassword("testUser", "testPassword", "newPassword");

        verify(traineeRepository, times(1)).changeTraineesPassword("testUser", "newPassword");
    }

    @Test
    void changeTraineesPassword_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> traineeService.changeTraineesPassword("testUser", "testPassword", "newPassword"));
    }

    @Test
    void activateTrainee_ShouldCallRepositoryActivateTrainee_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        traineeService.activateTrainee("testUser", "testPassword");

        verify(traineeRepository, times(1)).activateTrainee("testUser");
    }

    @Test
    void activateTrainee_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> traineeService.activateTrainee("testUser", "testPassword"));
    }

    @Test
    void deactivateTrainee_ShouldCallRepositoryDeactivateTrainee_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        traineeService.deactivateTrainee("testUser", "testPassword");

        verify(traineeRepository, times(1)).deactivateTrainee("testUser");
    }

    @Test
    void deactivateTrainee_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> traineeService.deactivateTrainee("testUser", "testPassword"));
    }
}
