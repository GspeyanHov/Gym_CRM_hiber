package org.epam.gymCrmHiber.service;

import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.repository.TrainerRepository;
import org.epam.gymCrmHiber.service.impl.TrainerServiceImpl;
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
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserDetailsService<Trainer> userDetailsService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer();
        trainer.setUsername("testUser");
        trainer.setPassword("testPassword");
        trainer.setFirstname("John");
        trainer.setLastname("Doe");
    }

    @Test
    void saveTrainer_ShouldCallRepositoryCreate() {
        trainerService.saveTrainer(trainer);
        verify(trainerRepository, times(1)).create(trainer);
    }

    @Test
    void updateTrainer_ShouldCallRepositoryUpdate_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        trainerService.updateTrainer(trainer, "testUser", "testPassword");

        verify(trainerRepository, times(1)).update(trainer);
    }

    @Test
    void updateTrainer_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainerService.updateTrainer(trainer, "testUser", "testPassword"));
    }

    @Test
    void getTrainerByUsername_ShouldReturnTrainer_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        when(trainerRepository.selectByUsername(anyString())).thenReturn(Optional.of(trainer));
        Optional<Trainer> result = trainerService.getTrainerByUsername("testUser", "testPassword");

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void getTrainerByUsername_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainerService.getTrainerByUsername("testUser", "testPassword"));
    }

    @Test
    void changeTrainersPassword_ShouldCallRepositoryChangeTrainersPassword_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        trainerService.changeTrainersPassword("testUser", "testPassword", "newPassword");

        verify(trainerRepository, times(1)).changeTrainersPassword("testUser", "newPassword");
    }

    @Test
    void changeTrainersPassword_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainerService.changeTrainersPassword("testUser", "testPassword", "newPassword"));
    }

    @Test
    void activateTrainer_ShouldCallRepositoryActivateTrainer_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        trainerService.activateTrainer("testUser", "testPassword");

        verify(trainerRepository, times(1)).activateTrainer("testUser");
    }

    @Test
    void activateTrainer_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainerService.activateTrainer("testUser", "testPassword"));
    }

    @Test
    void deactivateTrainer_ShouldCallRepositoryDeactivateTrainer_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        trainerService.deactivateTrainer("testUser", "testPassword");

        verify(trainerRepository, times(1)).deactivateTrainer("testUser");
    }

    @Test
    void deactivateTrainer_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainerService.deactivateTrainer("testUser", "testPassword"));
    }

    @Test
    void getUnassignedTrainers_ShouldReturnListOfTrainers_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        when(trainerRepository.getUnassignedTrainers(anyString())).thenReturn(Optional.of(List.of(trainer)));
        Optional<List<Trainer>> result = trainerService.getUnassignedTrainers("testUser", "testPassword", "traineeUsername");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(trainer, result.get().get(0));
    }

    @Test
    void getUnassignedTrainers_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainerService.getUnassignedTrainers("testUser", "testPassword", "traineeUsername"));
    }
}
