package com.example.documentsummarizer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatbotService {

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String OPENAI_API_KEY = ""; // Replace with your OpenAI API key

    public String chatWithBot(String userMessage, String documentSummary) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        // Create request payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        // Pass the document summary as context
        requestBody.put("messages", new Object[]{
            Map.of("role", "system", "content", "You are an AI assistant that answers questions based on the following document summary: " + documentSummary),
            Map.of("role", "user", "content", userMessage)
        });
        requestBody.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, an error occurred while processing your request.";
        }
    }
}
