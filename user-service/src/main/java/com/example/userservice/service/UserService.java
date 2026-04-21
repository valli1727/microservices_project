package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createUser(User user) {
        return repo.save(user);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUser(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
