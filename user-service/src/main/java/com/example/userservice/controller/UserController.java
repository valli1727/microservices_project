package com.example.userservice.controller;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = service.getUser(id);

        if (user == null) {
            return ResponseEntity.notFound().build(); // ✅ 404
        }

        return ResponseEntity.ok(user); // ✅ 200
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
