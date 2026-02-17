// ======================= controller/UserController.java =======================
package com.example.SpringBootApps.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.service.UserService;

/**
 * Profile Management Controller.
 *
 * Context path is set in application.properties:
 *   server.servlet.context-path=/api/v1
 *
 * So endpoints below are effectively:
 *   POST  /api/v1/users
 *   GET   /api/v1/users/{username}
 *   PATCH /api/v1/users/{username}
 */
@RestController
@RequestMapping("")
public class UserController {

    private final UserService userService;

    // Constructor injection (Autowired optional, but fine to keep for class clarity)
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create user
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> body) {
        // Delegates validation + creation to service
        Map<String, Object> createdUser = userService.createUser(body);
        return ResponseEntity.ok(createdUser);
    }

    // Retrieve user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Map<String, Object> user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    // Update user fields EXCEPT email
    @PatchMapping("/users/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @RequestBody Map<String, Object> updates) {
        userService.updateUserExceptEmail(username, updates);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated (email not changed)");
        return ResponseEntity.ok(response);
    
    }
}
