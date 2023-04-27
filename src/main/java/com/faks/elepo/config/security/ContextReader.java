package com.faks.elepo.config.security;

import com.faks.elepo.model.User;
import com.faks.elepo.database.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContextReader {
    private final UserRepository userRepository;

    public ContextReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedInUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
}
