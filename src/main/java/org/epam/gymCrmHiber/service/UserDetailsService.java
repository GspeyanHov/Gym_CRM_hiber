package org.epam.gymCrmHiber.service;

import org.epam.gymCrmHiber.entity.User;

public interface UserDetailsService<T extends User> {

    boolean authenticate(String username, String password);
}
