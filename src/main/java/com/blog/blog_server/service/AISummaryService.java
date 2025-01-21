package com.blog.blog_server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.blog.blog_server.model.AIRequestBody;
import com.blog.blog_server.model.AISummaryResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AISummaryService {
    private final String aiServiceEndpoint = "https://r682gu2f0f.execute-api.us-west-2.amazonaws.com/prod/api/microservice/ai";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AISummaryService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public AISummaryResponse getAISummary(AIRequestBody requestBody) {
        try {
            requestBody.getBody().setPrompt(requestBody.getBody().getPrompt() + "\n" + "Summerize what the article/blog above talks about in one to two(if necessary) paragraph in a third person tone. The response should be an html string that is going to be embeded as inner html, use style attribute to style the response and ttheme colors are #1DB954, white and black. Note that there is no need to add a background color and text gotta be white.");
            
            String rawResponse = restTemplate.postForObject(aiServiceEndpoint, requestBody, String.class);
            System.out.println("Raw response: " + rawResponse);
            JsonNode outerResponse = objectMapper.readTree(rawResponse);
            String innerJsonString = outerResponse.get("response").asText();
            
            JsonNode innerResponse = objectMapper.readTree(innerJsonString);
            
            String text = innerResponse
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

            AISummaryResponse aiSummaryResponse = new AISummaryResponse();
            aiSummaryResponse.setResponse(text);
            return aiSummaryResponse;
            
        } catch (Exception e) {
            System.err.println("Error processing AI response: " + e.getMessage());
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }
}