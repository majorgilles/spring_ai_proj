package com.example.aimobilebackend.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
  UUID id;
  String username;
  String email;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
