package com.weatherapp.weatherbackend.controller;

import com.weatherapp.weatherbackend.dto.WeatherResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // Constructor-based injection
    public WeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&appid=" + apiKey + "&units=metric";

            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                return ResponseEntity.status(500).body("Error fetching data from weather API.");
            }

            JSONObject json = new JSONObject(response);
            String cityName = json.getString("name");
            double temperature = json.getJSONObject("main").getDouble("temp");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            String icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");

            WeatherResponse weatherResponse = new WeatherResponse(cityName, temperature, description, icon);
            return ResponseEntity.ok(weatherResponse);

        } catch (RestClientException e) {
            return ResponseEntity.status(400).body("City not found or API unreachable.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
}
