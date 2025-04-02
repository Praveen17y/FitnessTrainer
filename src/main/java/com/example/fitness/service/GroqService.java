package com.example.fitness.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroqService {

    // ObjectMapper for JSON parsing and formatting.
    private final ObjectMapper objectMapper = new ObjectMapper();

    // API key injected from application properties.
    @Value("${groq.api.key}")
    private String groqApiKey;

    public String getWorkoutPlan(String fitnessLevel, String exerciseDetails, String userGoals, String injuryNotes) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        if (groqApiKey == null || groqApiKey.isBlank()) {

            return "<div class='alert alert-danger'>API key is missing</div>";
        }

        // Updated system prompt to request HTML output.
        String systemPrompt = String.format(
                "As an AI personal trainer, generate a complete workout and nutrition plan for a user with the following details:<br>" +
                        "Fitness Level: %s<br>" +
                        "Previous Exercises: %s<br>" +
                        "User Goals: %s<br>" +
                        "Injury Notes: %s<br><br>" +
                        "The response must be a fully formatted HTML snippet (without markdown or code formatting) that contains the following sections:<br>" +
                        "- Warm-up routine<br>" +
                        "- Main workout (exercises, sets, reps, form tips)<br>" +
                        "- Cool-down stretches<br>" +
                        "- Nutrition plan (macros, meals, hydration)<br>" +
                        "- Two sample workout scenarios<br><br>" +
                        "Ensure that the HTML is valid and can be directly rendered in a web page.",
                fitnessLevel, exerciseDetails, userGoals, injuryNotes
        );

        try {
            // Build the request body.
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama-3.3-70b-versatile");
            requestBody.put("temperature", 1);
            requestBody.put("max_completion_tokens", 1024);
            requestBody.put("top_p", 1);
            requestBody.put("stream", false);
            // Indicate that the response should be a plain text/HTML output.
            requestBody.put("response_format", Map.of("type", "text"));

            // Build the messages list required by the API.
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", systemPrompt));
            messages.add(Map.of("role", "user", "content", "Generate my personalized fitness plan as valid HTML"));
            requestBody.put("messages", messages);

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            // Set up HTTP headers.
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey);
            headers.set(HttpHeaders.ACCEPT, "application/json");
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();


                // Parse the raw API response.
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choices = rootNode.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    JsonNode contentNode = choices.get(0).path("message").path("content");
                    if (!contentNode.isMissingNode() && contentNode.isTextual()) {
                        // Directly return the HTML content without further parsing.
                        return contentNode.asText();
                    }
                }
                return "<div class='alert alert-danger'>Invalid response structure from API</div>";
            }
        }
        catch (Exception e) {

            return String.format("<div class='alert alert-danger'>API request failed: %s</div>", e.getMessage());
        }
        return "";

    }
}
