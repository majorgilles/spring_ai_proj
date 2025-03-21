package com.example.aimobilebackend.infrastructure.ai;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAiAssistantAdapter {

  private final ChatClient openAiChatClient;

  public String generateResponse(String prompt) {
    return openAiChatClient
        .call(new Prompt(new UserMessage(prompt)))
        .getResult()
        .getOutput()
        .getContent();
  }

  public String summarizeText(String text) {
    SystemPromptTemplate systemPromptTemplate =
        new SystemPromptTemplate(
            "You are a helpful assistant specialized in summarizing text. "
                + "Summarize the following text in a concise way. "
                + "TEXT: {text}");

    Message systemMessage = systemPromptTemplate.createMessage(Map.of("text", text));
    UserMessage userMessage = new UserMessage("Please provide a summary.");

    Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
    return openAiChatClient.call(prompt).getResult().getOutput().getContent();
  }

  public String getRecommendations(String context) {
    SystemPromptTemplate systemPromptTemplate =
        new SystemPromptTemplate(
            "You are a helpful assistant specialized in providing recommendations. "
                + "Based on the following context, provide relevant recommendations. "
                + "CONTEXT: {context}");

    Message systemMessage = systemPromptTemplate.createMessage(Map.of("context", context));
    UserMessage userMessage = new UserMessage("What would you recommend?");

    Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
    return openAiChatClient.call(prompt).getResult().getOutput().getContent();
  }
}
