package com.example.aimobilebackend.application.port.in;

import com.example.aimobilebackend.application.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {
    UserDto createUser(String username, String email);
    Optional<UserDto> getUserById(UUID id);
    List<UserDto> getAllUsers();
    Optional<UserDto> updateUsername(UUID id, String username);
    Optional<UserDto> updateEmail(UUID id, String email);
    void deleteUser(UUID id);
}