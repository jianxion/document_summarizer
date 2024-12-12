package com.example.documentsummarizer.service;

import com.example.documentsummarizer.model.OpenAIResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SummarizationService {

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    
    String apiKey = System.getenv("OPENAI_API_KEY");
    public String summarizeText(String text) {
        RestTemplate restTemplate = new RestTemplate();

        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Build the JSON request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo"); // Specify the LLM model
        requestBody.put("messages", new Object[] {
            Map.of("role", "system", "content", "You are an AI summarizer."),
            Map.of("role", "user", "content", "Summarize this text: " + text)
        });
        requestBody.put("max_tokens", 150);

        // Create the HTTP entity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Send the POST request to the OpenAI API
            ResponseEntity<OpenAIResponse> response = restTemplate.postForEntity(OPENAI_API_URL, entity, OpenAIResponse.class);

            // Extract the summary from the response
            OpenAIResponse responseBody = response.getBody();
            if (responseBody != null && responseBody.getChoices() != null && !responseBody.getChoices().isEmpty()) {
                return responseBody.getChoices().get(0).getMessage().getContent();
            }

            return "No summary returned from OpenAI.";
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return "Error during summarization: " + e.getMessage();
        }
    }
}
