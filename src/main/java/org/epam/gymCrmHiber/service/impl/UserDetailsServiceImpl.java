package org.epam.gymCrmHiber.service.impl;

import lombok.RequiredArgsConstructor;
import org.epam.gymCrmHiber.repository.UserRepository;
import org.epam.gymCrmHiber.entity.User;
import org.epam.gymCrmHiber.service.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl<T extends User> implements UserDetailsService<T> {

    private final UserRepository<T> userRepository;

    @Override
    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
