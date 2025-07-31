package com.cs3773.grocery.manager.sweproject.controller;

import org.springframework.web.bind.annotation.RestController;
import com.cs3773.grocery.manager.sweproject.objects.user;
import com.cs3773.grocery.manager.sweproject.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://35.184.41.71/")
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<user> registerUser(@RequestBody user user) {
        user registeredUser = loginService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<user> loginUser(@RequestBody user request) {
        user loggedInUser = loginService.login(request.getUsername(), request.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
}
