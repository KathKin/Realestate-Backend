package com.example.Laba7.controller;

import com.example.Laba7.model.User;
import com.example.Laba7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.Laba7.model.UserRole;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Optional<User> user = userService.login(email, password);

        Map<String, Object> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("success", true);
            response.put("user", user.get());
        } else {
            response.put("success", false);
            response.put("message", "Неверный email или пароль");
        }
        return response;
    }

    @GetMapping("/check-realtor")
    public ResponseEntity<Map<String, Object>> checkRealtor(@RequestParam String email) {
        Optional<User> user = userService.findByEmail(email);

        Map<String, Object> response = new HashMap<>();

        if (user.isPresent()) {
            User currentUser = user.get();

            boolean isRealtor = currentUser.getRole() == UserRole.AGENT;

            response.put("isRealtor", isRealtor);
            response.put("role", currentUser.getRole().name());
            return ResponseEntity.ok(response);
        } else {
            response.put("isRealtor", false);
            response.put("message", "Пользователь не найден");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}