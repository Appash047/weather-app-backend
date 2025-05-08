package com.weatherapp.weatherbackend.dto;

public class WeatherResponse {
    private String city;
    private double temperature;
    private String description;
    private String icon;

    public WeatherResponse(String city, double temperature, String description, String icon) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
        this.icon = icon;
    }

    public WeatherResponse() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}