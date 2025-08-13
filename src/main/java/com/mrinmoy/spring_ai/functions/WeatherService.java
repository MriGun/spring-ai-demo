/*
package com.mrinmoy.spring_ai.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;


public class WeatherService implements Function<WeatherService.Request, WeatherService.WeatherResponse> {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private final RestClient restClient;
    private final WeatherConfigProperties weatherProps;

    public WeatherService(WeatherConfigProperties weatherProps) {
        this.weatherProps = weatherProps;
        log.debug("Weather API URL: {}", weatherProps.apiUrl());
        log.debug("Weather API Key: {}", weatherProps.apiKey());
        this.restClient = RestClient.create(weatherProps.apiUrl());
    }

    @Override
    public WeatherResponse apply(Request request) {
        log.info("Weather Request: {}",request);
        WeatherResponse response = restClient.get()
                .uri("/current.json?key={key}&q={q}", weatherProps.apiKey(), request.city())
                .retrieve()
                .body(WeatherResponse.class);
        log.info("Weather API Response: {}", response);
        return response;
    }

    // mapping the response of the Weather API to records. I only mapped the information I was interested in.
    public record Request(String city) {}
    public record WeatherResponse(Location location,Current current) {}
    public record Location(String name, String region, String country, Long lat, Long lon){}
    public record Current(String temp_f, Condition condition, String wind_mph, String humidity) {}
    public record Condition(String text){}

}
*/
