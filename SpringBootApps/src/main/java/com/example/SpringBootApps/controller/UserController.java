/* ========= controller/UserController.java ========= */
package com.example.SpringBootApps.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.AppUser;
import com.example.SpringBootApps.service.UserService;

/**
 * Profile Management Controller.
 * Endpoints:
 *  - POST /users              create profile (requires username + password)
 *  - GET  /users/{username}   retrieve profile by username
 *
 * NOTE: This controller returns sanitized JSON and never returns passwordHash.
 */
@RestController
@RequestMapping("")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> body) {

        String username = body.get("username") == null ? null : body.get("username").toString();
        String password = body.get("password") == null ? null : body.get("password").toString();
        String name = body.get("name") == null ? null : body.get("name").toString();
        String email = body.get("email") == null ? null : body.get("email").toString();
        String homeAddress = body.get("homeAddress") == null ? null : body.get("homeAddress").toString();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "username and password are required"));
        }

        AppUser saved = userService.createUser(username, password, name, email, homeAddress);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", saved.getUserId());
        response.put("username", saved.getUsername());
        response.put("name", saved.getName());
        response.put("email", saved.getEmail());
        response.put("homeAddress", saved.getHomeAddress());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        Optional<AppUser> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AppUser user = userOpt.get();

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("username", user.getUsername());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("homeAddress", user.getHomeAddress());

        return ResponseEntity.ok(response);
    }
}
