package com.example.aimobilebackend.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.aimobilebackend.application.dto.UserDto;
import com.example.aimobilebackend.domain.model.aggregate.User;
import com.example.aimobilebackend.domain.model.valueobject.UserId;
import com.example.aimobilebackend.domain.repository.UserRepository;

class UserApplicationServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserApplicationService userService;

  private User testUser;
  private UUID testUuid;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    testUuid = UUID.randomUUID();
    testUser =
        User.builder()
            .id(UserId.of(testUuid))
            .username("testuser")
            .email("test@example.com")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void createUser_shouldCreateAndReturnUserDto() {
    // Given
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    // When
    UserDto result = userService.createUser("testuser", "test@example.com");

    // Then
    assertNotNull(result);
    assertEquals(testUuid, result.getId());
    assertEquals("testuser", result.getUsername());
    assertEquals("test@example.com", result.getEmail());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void getUserById_shouldReturnUserDtoWhenFound() {
    // Given
    when(userRepository.findById(any(UserId.class))).thenReturn(Optional.of(testUser));

    // When
    Optional<UserDto> result = userService.getUserById(testUuid);

    // Then
    assertTrue(result.isPresent());
    assertEquals(testUuid, result.get().getId());
    assertEquals("testuser", result.get().getUsername());
    assertEquals("test@example.com", result.get().getEmail());
    verify(userRepository, times(1)).findById(any(UserId.class));
  }

  @Test
  void getUserById_shouldReturnEmptyWhenNotFound() {
    // Given
    when(userRepository.findById(any(UserId.class))).thenReturn(Optional.empty());

    // When
    Optional<UserDto> result = userService.getUserById(testUuid);

    // Then
    assertFalse(result.isPresent());
    verify(userRepository, times(1)).findById(any(UserId.class));
  }

  @Test
  void getAllUsers_shouldReturnAllUsers() {
    // Given
    User testUser2 =
        User.builder()
            .id(UserId.of(UUID.randomUUID()))
            .username("testuser2")
            .email("test2@example.com")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, testUser2));

    // When
    List<UserDto> results = userService.getAllUsers();

    // Then
    assertEquals(2, results.size());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void updateUsername_shouldUpdateAndReturnUserDtoWhenFound() {
    // Given
    User updatedUser =
        User.builder()
            .id(UserId.of(testUuid))
            .username("updateduser")
            .email("test@example.com")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    when(userRepository.findById(any(UserId.class))).thenReturn(Optional.of(testUser));
    when(userRepository.save(any(User.class))).thenReturn(updatedUser);

    // When
    Optional<UserDto> result = userService.updateUsername(testUuid, "updateduser");

    // Then
    assertTrue(result.isPresent());
    assertEquals("updateduser", result.get().getUsername());
    assertEquals("test@example.com", result.get().getEmail());
    verify(userRepository, times(1)).findById(any(UserId.class));
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void deleteUser_shouldCallRepositoryToDeleteUser() {
    // When
    userService.deleteUser(testUuid);

    // Then
    verify(userRepository, times(1)).delete(any(UserId.class));
  }
}
