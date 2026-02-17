package com.example.SpringBootApps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.AppUser;
import com.example.SpringBootApps.repository.AppUserRepository;

/**
 * Service layer for Profile Management.
 * Handles:
 *  - Username uniqueness validation
 *  - Saving user to database
 *  - Retrieving user by username
 *
 * NOTE: Password is stored as-is (no hashing).
 * This is acceptable for a class project but not production.
 */
@Service
public class UserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser createUser(String username,
                              String password,
                              String name,
                              String email,
                              String homeAddress) {

        if (appUserRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(username);

        // Store password directly (no hashing)
        user.setPasswordHash(password);

        user.setName(name);
        user.setEmail(email);
        user.setHomeAddress(homeAddress);

        return appUserRepository.save(user);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
