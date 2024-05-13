package com.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Represents a city type with its name.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityType {
    /**
     * The name of the city.
     */
    @JsonProperty("name")
    private String name;
    
}