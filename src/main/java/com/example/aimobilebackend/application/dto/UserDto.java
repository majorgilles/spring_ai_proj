package com.example.aimobilebackend.application.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class UserDto {
    UUID id;
    String username;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}