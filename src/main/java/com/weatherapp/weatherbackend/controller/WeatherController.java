package com.weatherapp.weatherbackend.controller;

import com.weatherapp.weatherbackend.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*") // Allow CORS from all origins (change if needed)
public class WeatherController {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // Constructor-based injection for better testing
    public WeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&appid=" + apiKey + "&units=metric";

            // Fetch the response and map it to WeatherResponse directly
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

            if (weatherResponse == null) {
                return ResponseEntity.status(500).body("Error fetching data from weather API.");
            }

            return ResponseEntity.ok(weatherResponse);
        } catch (RestClientException e) {
            // Handle case where city is not found or the API is unreachable
            return ResponseEntity.status(400).body("City not found or API unreachable.");
        } catch (Exception e) {
            // General error handling
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
}
