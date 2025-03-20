package com.example.aimobilebackend.application.port.in;

public interface AiAssistantUseCase {
    String generateResponse(String prompt);
    String summarizeText(String text);
    String getRecommendations(String context);
}