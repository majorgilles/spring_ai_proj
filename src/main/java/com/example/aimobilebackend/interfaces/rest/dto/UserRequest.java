package com.example.aimobilebackend.interfaces.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

  @NotBlank(message = "Username is required")
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;
}
