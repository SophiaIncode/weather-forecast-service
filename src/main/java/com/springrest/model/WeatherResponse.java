package com.springrest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing weather information including the count, weather data list, and city information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class WeatherResponse {
    
    public WeatherResponse(List<TempData> weatherData) {
		this.list=weatherData;
	}

	/**
     * The count of weather data entries.
     */
    @JsonProperty("cnt")
    private Integer cnt;
    
    /**
     * The list of weather data.
     */
    @JsonProperty("list")
    private List<TempData> list;
    
    /**
     * The city information.
     */
    @JsonProperty("city")
    private CityType city;

}