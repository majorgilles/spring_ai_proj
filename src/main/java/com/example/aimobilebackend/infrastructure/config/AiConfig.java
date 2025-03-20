package com.example.aimobilebackend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

@Configuration
public class AiConfig {

    @Bean
    public SystemMessage defaultSystemMessage() {
        return new SystemMessage("You are a helpful AI assistant for a mobile app. " +
                "Provide concise, accurate, and helpful responses.");
    }
    
    @Bean
    public SystemPromptTemplate summarizerSystemPrompt() {
        return new SystemPromptTemplate(
                "You are a text summarization expert. " +
                "Summarize the following text in a clear and concise manner, " +
                "capturing the key points and main ideas. " +
                "Keep the summary informative but brief. " +
                "TEXT: {text}");
    }
    
    @Bean
    public SystemPromptTemplate recommenderSystemPrompt() {
        return new SystemPromptTemplate(
                "You are a recommendation expert. " +
                "Based on the following context, provide personalized recommendations. " +
                "Your recommendations should be relevant, useful, and tailored to the information provided. " +
                "CONTEXT: {context}");
    }
}