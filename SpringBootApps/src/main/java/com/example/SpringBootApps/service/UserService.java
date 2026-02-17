// ========================= service/UserService.java ==========================
package com.example.SpringBootApps.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.AppUser;
import com.example.SpringBootApps.repository.AppUserRepository;

/**
 * Service layer for Profile Management.
 * - Handles business logic
 * - Talks to repository (DB)
 * - Enforces: update allowed fields except email
 */
@Service
public class UserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Creates a new user from request JSON.
     * Expected keys in body:
     * - username (required)
     * - password (required)
     * - name (optional)
     * - email (optional)
     * - homeAddress (optional)
     *
     * Returns a sanitized response map (no passwordHash).
     */
    public Map<String, Object> createUser(Map<String, Object> body) {
        String username = body.get("username") == null ? null : body.get("username").toString();
        String password = body.get("password") == null ? null : body.get("password").toString();
        String name = body.get("name") == null ? null : body.get("name").toString();
        String email = body.get("email") == null ? null : body.get("email").toString();
        String homeAddress = body.get("homeAddress") == null ? null : body.get("homeAddress").toString();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("username and password are required");
        }

        if (appUserRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(username);

        // Stored in passwordHash column for now (project decision)
        user.setPasswordHash(password);

        user.setName(name);
        user.setEmail(email);
        user.setHomeAddress(homeAddress);

        AppUser saved = appUserRepository.save(user);

        return sanitizeUser(saved);
    }

    /**
     * Returns sanitized user by username.
     */
    public Map<String, Object> getUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return sanitizeUser(user);
    }

    /**
     * Updates allowed fields except email.
     * Allowed keys:
     * - password
     * - name
     * - homeAddress
     *
     * Email is ignored even if provided.
     */
    public void updateUserExceptEmail(String username, Map<String, Object> updates) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updates.containsKey("password")) {
            Object pw = updates.get("password");
            if (pw != null && !pw.toString().isBlank()) {
                user.setPasswordHash(pw.toString());
            }
        }

        if (updates.containsKey("name")) {
            Object name = updates.get("name");
            user.setName(name == null ? null : name.toString());
        }

        if (updates.containsKey("homeAddress")) {
            Object addr = updates.get("homeAddress");
            user.setHomeAddress(addr == null ? null : addr.toString());
        }

        // Email intentionally NOT updated
        appUserRepository.save(user);
    }

    /**
     * Builds a response map without exposing passwordHash.
     */
    private Map<String, Object> sanitizeUser(AppUser user) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("username", user.getUsername());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("homeAddress", user.getHomeAddress());
        return response;
    }
}
