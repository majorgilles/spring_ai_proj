package com.example.aimobilebackend.domain.model.valueobject;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserId {
  private final UUID value;

  private UserId(UUID value) {
    this.value = value;
  }

  public static UserId create() {
    return new UserId(UUID.randomUUID());
  }

  public static UserId of(UUID id) {
    return new UserId(id);
  }

  public static UserId of(String id) {
    return new UserId(UUID.fromString(id));
  }
}
