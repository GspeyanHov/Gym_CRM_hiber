package org.epam.gymCrmHiber.repository;

import java.util.Optional;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.entity.User;
import org.epam.gymCrmHiber.repository.impl.UserRepositoryImpl;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class UserRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    @InjectMocks
    private UserRepositoryImpl<User> userRepository;

    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createCriteria(User.class)).thenReturn(criteria);
    }

    @Test
    @Deprecated
    public void testFindByUsername_WhenUserExists_ReturnsUser() {
        String username = "testuser";
        User user = new Trainer();
        when(criteria.uniqueResult()).thenReturn(user);
        Optional<User> result = userRepository.findByUsername(username);
        assertEquals(Optional.of(user), result);
    }

    @Test
    @Deprecated
    public void testFindByUsername_WhenUserDoesNotExist_ReturnsEmptyOptional() {
        String username = "nonexistentuser";
        when(criteria.uniqueResult()).thenReturn(null);
        Optional<User> result = userRepository.findByUsername(username);
        assertEquals(Optional.empty(), result);
    }
}
