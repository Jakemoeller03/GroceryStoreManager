package com.cs3773.grocery.manager.sweproject.repository;

import com.cs3773.grocery.manager.sweproject.objects.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Integer> {

    // Custom query method to find a user by username
    Optional<user> findByUsername(String username);
}

