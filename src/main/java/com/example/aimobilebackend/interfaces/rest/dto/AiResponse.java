package com.example.aimobilebackend.interfaces.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiResponse {
  private String content;
}
