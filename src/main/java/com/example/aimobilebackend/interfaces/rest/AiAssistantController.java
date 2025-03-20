package com.example.aimobilebackend.interfaces.rest;

import com.example.aimobilebackend.infrastructure.ai.OpenAiAssistantAdapter;
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
    
    private final OpenAiAssistantAdapter openAiAssistantAdapter;
    
    @PostMapping("/generate")
    public ResponseEntity<AiResponse> generateResponse(@Valid @RequestBody AiRequest request) {
        String generatedContent = openAiAssistantAdapter.generateResponse(request.getText());
        return ResponseEntity.ok(AiResponse.builder().content(generatedContent).build());
    }
    
    @PostMapping("/summarize")
    public ResponseEntity<AiResponse> summarizeText(@Valid @RequestBody AiRequest request) {
        String summary = openAiAssistantAdapter.summarizeText(request.getText());
        return ResponseEntity.ok(AiResponse.builder().content(summary).build());
    }
    
    @PostMapping("/recommend")
    public ResponseEntity<AiResponse> getRecommendations(@Valid @RequestBody AiRequest request) {
        String recommendations = openAiAssistantAdapter.getRecommendations(request.getText());
        return ResponseEntity.ok(AiResponse.builder().content(recommendations).build());
    }
}