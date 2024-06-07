package org.epam.gymCrmHiber.service;

import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.entity.User;
import org.epam.gymCrmHiber.repository.UserRepository;
import org.epam.gymCrmHiber.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository<User> userRepository;

    @InjectMocks
    private UserDetailsServiceImpl<User> userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new Trainer();
        user.setUsername("testUser");
        user.setPassword("testPassword");
    }

    @Test
    void authenticate_ShouldReturnTrue_WhenCredentialsAreValid() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        boolean result = userDetailsService.authenticate("testUser", "testPassword");
        assertTrue(result);
    }

    @Test
    void authenticate_ShouldReturnFalse_WhenPasswordIsInvalid() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        boolean result = userDetailsService.authenticate("testUser", "wrongPassword");
        assertFalse(result);
    }

    @Test
    void authenticate_ShouldReturnFalse_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());
        boolean result = userDetailsService.authenticate("nonExistentUser", "testPassword");
        assertFalse(result);
    }
}
