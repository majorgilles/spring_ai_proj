package com.example.aimobilebackend.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.aimobilebackend.application.dto.UserDto;
import com.example.aimobilebackend.domain.model.aggregate.User;
import com.example.aimobilebackend.domain.model.valueobject.UserId;
import com.example.aimobilebackend.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

  private final UserRepository userRepository;

  public UserDto createUser(String username, String email) {
    User user = User.create(username, email);
    return mapToDto(userRepository.save(user));
  }

  public Optional<UserDto> getUserById(UUID id) {
    return userRepository.findById(UserId.of(id)).map(this::mapToDto);
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
  }

  public Optional<UserDto> updateUsername(UUID id, String username) {
    return userRepository
        .findById(UserId.of(id))
        .map(
            user -> {
              user.updateUsername(username);
              return userRepository.save(user);
            })
        .map(this::mapToDto);
  }

  public Optional<UserDto> updateEmail(UUID id, String email) {
    return userRepository
        .findById(UserId.of(id))
        .map(
            user -> {
              user.updateEmail(email);
              return userRepository.save(user);
            })
        .map(this::mapToDto);
  }

  public void deleteUser(UUID id) {
    userRepository.delete(UserId.of(id));
  }

  private UserDto mapToDto(User user) {
    return UserDto.builder()
        .id(user.getId().getValue())
        .username(user.getUsername())
        .email(user.getEmail())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
