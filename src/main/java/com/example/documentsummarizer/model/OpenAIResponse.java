package com.example.documentsummarizer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// Root response object
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponse {

    @JsonProperty("choices")
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    // Inner choice object
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        @JsonProperty("message")
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    // Inner message object
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        @JsonProperty("content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
