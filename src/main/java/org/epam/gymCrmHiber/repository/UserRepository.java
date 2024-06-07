package org.epam.gymCrmHiber.repository;

import java.util.Optional;
import org.epam.gymCrmHiber.entity.User;

public interface UserRepository <T extends User> {

    Optional<T> findByUsername(String username);
}
