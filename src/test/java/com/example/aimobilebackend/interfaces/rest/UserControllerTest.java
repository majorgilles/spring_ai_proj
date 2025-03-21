package com.example.aimobilebackend.interfaces.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.aimobilebackend.application.dto.UserDto;
import com.example.aimobilebackend.application.service.UserApplicationService;
import com.example.aimobilebackend.interfaces.rest.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private UserApplicationService userApplicationService;

  private UserDto testUserDto;
  private UUID testUuid;

  @BeforeEach
  void setUp() {
    testUuid = UUID.randomUUID();
    testUserDto =
        UserDto.builder()
            .id(testUuid)
            .username("testuser")
            .email("test@example.com")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void createUser_shouldReturnCreatedUser() throws Exception {
    // Given
    UserRequest request = new UserRequest();
    request.setUsername("testuser");
    request.setEmail("test@example.com");

    when(userApplicationService.createUser(anyString(), anyString())).thenReturn(testUserDto);

    // When & Then
    mockMvc
        .perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(testUuid.toString()))
        .andExpect(jsonPath("$.username").value("testuser"))
        .andExpect(jsonPath("$.email").value("test@example.com"));
  }

  @Test
  void getUserById_shouldReturnUserWhenFound() throws Exception {
    // Given
    when(userApplicationService.getUserById(any(UUID.class))).thenReturn(Optional.of(testUserDto));

    // When & Then
    mockMvc
        .perform(get("/api/users/{id}", testUuid))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(testUuid.toString()))
        .andExpect(jsonPath("$.username").value("testuser"))
        .andExpect(jsonPath("$.email").value("test@example.com"));
  }

  @Test
  void getUserById_shouldReturn404WhenNotFound() throws Exception {
    // Given
    when(userApplicationService.getUserById(any(UUID.class))).thenReturn(Optional.empty());

    // When & Then
    mockMvc.perform(get("/api/users/{id}", testUuid)).andExpect(status().isNotFound());
  }

  @Test
  void getAllUsers_shouldReturnAllUsers() throws Exception {
    // Given
    UserDto testUserDto2 =
        UserDto.builder()
            .id(UUID.randomUUID())
            .username("testuser2")
            .email("test2@example.com")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    when(userApplicationService.getAllUsers()).thenReturn(Arrays.asList(testUserDto, testUserDto2));

    // When & Then
    mockMvc
        .perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].username").value("testuser"))
        .andExpect(jsonPath("$[1].username").value("testuser2"));
  }

  @Test
  void updateUsername_shouldReturnUpdatedUserWhenFound() throws Exception {
    // Given
    UserDto updatedUserDto =
        UserDto.builder()
            .id(testUuid)
            .username("updateduser")
            .email("test@example.com")
            .createdAt(testUserDto.getCreatedAt())
            .updatedAt(LocalDateTime.now())
            .build();

    when(userApplicationService.updateUsername(any(UUID.class), anyString()))
        .thenReturn(Optional.of(updatedUserDto));

    // When & Then
    mockMvc
        .perform(put("/api/users/{id}/username", testUuid).param("username", "updateduser"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("updateduser"));
  }

  @Test
  void deleteUser_shouldReturn204() throws Exception {
    // When & Then
    mockMvc.perform(delete("/api/users/{id}", testUuid)).andExpect(status().isNoContent());
  }
}
