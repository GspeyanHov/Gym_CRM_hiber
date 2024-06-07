package org.epam.gymCrmHiber.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.epam.gymCrmHiber.entity.User;
import org.epam.gymCrmHiber.repository.TrainingRepository;
import org.epam.gymCrmHiber.service.impl.TrainingServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserDetailsService<User> userDetailsService;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training training;
    private TrainingType trainingType;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    @BeforeEach
    void setUp() {
        training = new Training();
        trainingType = new TrainingType();
        User user = new Trainer();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        fromDate = LocalDateTime.now().minusDays(7);
        toDate = LocalDateTime.now();
    }

    @Test
    void createTraining_ShouldCallRepositoryCreate_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        trainingService.createTraining(training, "testUser", "testPassword");

        verify(trainingRepository, times(1)).create(training);
    }

    @Test
    void createTraining_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainingService.createTraining(training, "testUser", "testPassword"));
    }

    @Test
    void getTrainingByType_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainingService.getTrainingByType(trainingType, "testUser", "testPassword"));
    }

    @Test
    void getTraineeTrainings_ShouldReturnTrainings_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        when(trainingRepository.getTraineeTrainings(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyString(), any(TrainingType.class)))
                .thenReturn(Optional.of(List.of(training)));
        Optional<List<Training>> result = trainingService.getTraineeTrainings("testUser", "testPassword", fromDate, toDate, "trainerName", trainingType);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(training, result.get().get(0));
    }

    @Test
    void getTraineeTrainings_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainingService.getTraineeTrainings("testUser", "testPassword", fromDate, toDate, "trainerName", trainingType));
    }

    @Test
    void getTrainerTrainings_ShouldReturnTrainings_WhenAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(true);
        when(trainingRepository.getTrainerTrainings(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(Optional.of(List.of(training)));
        Optional<List<Training>> result = trainingService.getTrainerTrainings("testUser", "testPassword", fromDate, toDate, "traineeName");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(training, result.get().get(0));
    }

    @Test
    void getTrainerTrainings_ShouldThrowSecurityException_WhenNotAuthenticated() {
        when(userDetailsService.authenticate(anyString(), anyString())).thenReturn(false);
        assertThrows(SecurityException.class, () -> trainingService.getTrainerTrainings("testUser", "testPassword", fromDate, toDate, "traineeName"));
    }
}
