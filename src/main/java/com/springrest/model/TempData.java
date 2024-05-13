package com.springrest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Represents temperature data including temperature details, weather information, wind information, and timestamps.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TempData {

    /**
     * The temperature details.
     */
    @JsonProperty("main")
    private TempDetails tempDetails;
    
    /**
     * The weather information.
     */
    @JsonProperty("weather")
    private List<Weather> weather;
    
    /**
     * The wind information.
     */
    @JsonProperty("wind")
    private Wind wind;
    
    /**
     * The timestamp in seconds.
     */
    @JsonProperty("dt")
    private String dt;
    
    /**
     * The date and time in text format.
     */
    @JsonProperty("dt_txt")
    private String dt_txt;
    
}