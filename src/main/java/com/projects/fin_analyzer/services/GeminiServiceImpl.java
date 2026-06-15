package com.projects.fin_analyzer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiServiceImpl implements GeminiService{

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient
            .builder()
            .baseUrl("https://generativelanguage.googleapis.com")
            .build();

    public String generateInsights(String prompt){
        String url = "/v1beta/models/gemini-2.0-flash:generateContent?key=" +
                apiKey;

        Map<String, Object> body = Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        return webClient.post()
                .uri(url)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


}
