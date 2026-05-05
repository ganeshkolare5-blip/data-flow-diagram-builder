package com.internship.tool.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiServiceClient {

    public String getAiResponse(String input) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "http://localhost:5000/describe";

            Map<String, String> request = new HashMap<>();
            request.put("input", input);

            Object response = restTemplate.postForObject(url, request, Object.class);

            return response.toString();

        } catch (Exception e) {
            return null;
        }
    }
}