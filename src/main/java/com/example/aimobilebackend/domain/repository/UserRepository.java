package com.example.aimobilebackend.domain.repository;

import com.example.aimobilebackend.domain.model.aggregate.User;
import com.example.aimobilebackend.domain.model.valueobject.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    List<User> findAll();
    void delete(UserId id);
}