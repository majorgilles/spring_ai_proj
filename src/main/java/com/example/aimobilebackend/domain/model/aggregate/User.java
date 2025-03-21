package com.example.aimobilebackend.domain.model.aggregate;

import java.time.LocalDateTime;

import com.example.aimobilebackend.domain.model.valueobject.UserId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
  private final UserId id;
  private String username;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private User(
      UserId id, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static User create(String username, String email) {
    LocalDateTime now = LocalDateTime.now();
    return User.builder()
        .id(UserId.create())
        .username(username)
        .email(email)
        .createdAt(now)
        .updatedAt(now)
        .build();
  }

  public void updateUsername(String username) {
    this.username = username;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateEmail(String email) {
    this.email = email;
    this.updatedAt = LocalDateTime.now();
  }

  public static User createFromEntity(
      UserId id, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return User.builder()
        .id(id)
        .username(username)
        .email(email)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }
}
