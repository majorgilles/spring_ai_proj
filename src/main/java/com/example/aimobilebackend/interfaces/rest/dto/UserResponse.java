package com.example.aimobilebackend.interfaces.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
  private UUID id;
  private String username;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
