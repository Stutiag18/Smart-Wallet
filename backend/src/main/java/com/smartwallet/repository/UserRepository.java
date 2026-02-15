package com.smartwallet.repository;

import com.smartwallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Find by email (used for login / registration check)
    Optional<User> findByEmail(String email);

    // Find by business userId
    Optional<User> findByUserId(String userId);

    // Check existence (fast & efficient)
    boolean existsByEmail(String email);
}
