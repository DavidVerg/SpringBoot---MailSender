package com.example.reportes.service;

import com.example.reportes.exception.UserNotFoundException;
import com.example.reportes.model.User;
import com.example.reportes.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) { this.userRepo = userRepo; }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findUserByUserId(id).
                orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found."));
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteUserByUserId(id);
    }

}
