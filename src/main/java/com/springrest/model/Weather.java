package com.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Represents weather information including the main weather condition and its description.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Weather {
    
    /**
     * The main weather condition.
     */
    @JsonProperty("main")
    private String main;
    
    /**
     * The description of the weather condition.
     */
    @JsonProperty("description")
    private String description;

}