package com.example.aimobilebackend.domain.service;

import com.example.aimobilebackend.domain.model.aggregate.User;
import com.example.aimobilebackend.domain.model.valueobject.UserId;
import com.example.aimobilebackend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public User createUser(String username, String email) {
        User user = User.create(username, email);
        return userRepository.save(user);
    }
    
    public Optional<User> getUser(UserId id) {
        return userRepository.findById(id);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> updateUsername(UserId id, String username) {
        return userRepository.findById(id)
                .map(user -> {
                    user.updateUsername(username);
                    return userRepository.save(user);
                });
    }
    
    public Optional<User> updateEmail(UserId id, String email) {
        return userRepository.findById(id)
                .map(user -> {
                    user.updateEmail(email);
                    return userRepository.save(user);
                });
    }
    
    public void deleteUser(UserId id) {
        userRepository.delete(id);
    }
}