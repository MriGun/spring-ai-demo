package com.mrinmoy.spring_ai.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherServiceTool {

    private static final Logger log = LoggerFactory.getLogger(WeatherServiceTool.class);

    private final RestClient restClient;
    //private final WeatherConfigProperties weatherProps;

    public WeatherServiceTool() {
        //this.weatherProps = weatherProps;
        this.restClient = RestClient.create("http://api.weatherapi.com/v1");

    }

    @Tool(description = "Get the current weather conditions for the given city.")
    public WeatherResponse getWeather(String location) {
        // location is only used for logging. The coordinates are hardcoded in the URL
        log.info("getWeather tool was invoked with location: " + location);
        WeatherResponse response = null;
        String apikey = System.getenv("WEATHER_API_KEY");
        try {
            response = restClient.get()
                    .uri("/current.json?key={key}&q={q}", apikey, location)
                    .retrieve()
                    .body(WeatherResponse.class);
            log.info("Weather API Response: {}", response);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return response;
    }


    public record WeatherResponse(Location location,Current current) {}
    public record Location(String name, String region, String country, Long lat, Long lon){}
    public record Current(String temp_f, Condition condition, String wind_mph, String humidity) {}
    public record Condition(String text){}

}
