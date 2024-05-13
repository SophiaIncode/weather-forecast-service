package com.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Represents temperature details including the current temperature, maximum temperature, and minimum temperature.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TempDetails {

    /**
     * The current temperature.
     */
    @JsonIgnoreProperties("temp")
    private double temperature;

    /**
     * The maximum temperature.
     */
    @JsonProperty("temp_max")
    private double temp_max;
    
    /**
     * The minimum temperature.
     */
    @JsonProperty("temp_min")
    private double temp_min;
    

}