package com.projects.fin_analyzer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenRouterServiceImpl implements OpenRouterService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient
            .builder()
            .baseUrl("https://openrouter.ai")
            .build();

    public String generateInsights(String prompt){
        String url = "/api/v1/chat/completions";

        Map<String, Object> body = Map.of(
                "model",
                "poolside/laguna-m.1:free",

                "messages",
                List.of(
                        Map.of(
                                "role",
                                "user",
                                "content",
                                prompt
                        )
                )
        );

        Map response =  webClient.post()
                .uri(url)
                .header(
                        "Authorization",
                        "Bearer "+apiKey
                )
                .header(
                        "Content-type",
                        "application/json"
                )
                .bodyValue(body)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(errorBody -> {
                                    System.out.println("ERROR BODY = " + errorBody);
                                    return new RuntimeException(errorBody);
                                })
                )
                .bodyToMono(Map.class)
                .block();

        List choices =
                (List) response.get("choices");

        Map choice =
                (Map) choices.get(0);

        Map message =
                (Map) choice.get("message");

        return (String) message.get("content");
    }
}
