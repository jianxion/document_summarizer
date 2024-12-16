package com.example.documentsummarizer.controller;

import com.example.documentsummarizer.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        String documentSummary = payload.get("summary"); // Pass the summary from the front end
        String response = chatbotService.chatWithBot(userMessage, documentSummary);
        return ResponseEntity.ok(response);
    }
}
