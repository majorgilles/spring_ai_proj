package com.example.aimobilebackend.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiRequest {

  @NotBlank(message = "Input text is required")
  private String text;
}
