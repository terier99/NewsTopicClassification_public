package com.example.apiprac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;

@Service
public class NewsArticlePredictionService {
    //private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={apiKey}&units=metric";
    private static final String PREDICT_URL = "http://13.125.20.126:5000/predict?article={article}";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public NewsArticlePredictionService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public NewsArticlePrediction getNewsArticlePrediction(String article) {
        //URI url = new UriTemplate(WEATHER_URL).expand(city, country, apiKey);
        URI url = new UriTemplate(PREDICT_URL).expand(article);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return convert(response);
    }

    private NewsArticlePrediction convert(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
//            return new CurrentWeather(root.path("weather").get(0).path("main").asText(),
//                    root.path("main").path("temp").asText(),
//                    root.path("main").path("feels_like").asText(),
//                    root.path("wind").path("speed").asText());
            return new NewsArticlePrediction(root.path("output1").asText(),
                    root.path("output1probs").asText(),
                    root.path("output2").asText(),
                    root.path("output2probs").asText(),
                    root.path("output3").asText(),
                    root.path("output3probs").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}
