package com.cs3773.grocery.manager.sweproject.service;

import com.cs3773.grocery.manager.sweproject.objects.user;
import com.cs3773.grocery.manager.sweproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public user registerUser(user user) {
        return userRepository.save(user);
    }

    // Log in user by verifying username and password
    public user login(String username, String password) {
        Optional<user> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            user user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
