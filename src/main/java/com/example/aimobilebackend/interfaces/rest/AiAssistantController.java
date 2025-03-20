package com.example.aimobilebackend.interfaces.rest;

import com.example.aimobilebackend.application.port.in.AiAssistantUseCase;
import com.example.aimobilebackend.interfaces.rest.dto.AiRequest;
import com.example.aimobilebackend.interfaces.rest.dto.AiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAssistantController {
    
    private final AiAssistantUseCase aiAssistantUseCase;
    
    @PostMapping("/generate")
    public ResponseEntity<AiResponse> generateResponse(@Valid @RequestBody AiRequest request) {
        String generatedContent = aiAssistantUseCase.generateResponse(request.getText());
        return ResponseEntity.ok(AiResponse.builder().content(generatedContent).build());
    }
    
    @PostMapping("/summarize")
    public ResponseEntity<AiResponse> summarizeText(@Valid @RequestBody AiRequest request) {
        String summary = aiAssistantUseCase.summarizeText(request.getText());
        return ResponseEntity.ok(AiResponse.builder().content(summary).build());
    }
    
    @PostMapping("/recommend")
    public ResponseEntity<AiResponse> getRecommendations(@Valid @RequestBody AiRequest request) {
        String recommendations = aiAssistantUseCase.getRecommendations(request.getText());
        return ResponseEntity.ok(AiResponse.builder().content(recommendations).build());
    }
}