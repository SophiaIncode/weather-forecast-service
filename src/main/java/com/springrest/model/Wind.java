package com.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Represents wind information including wind speed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Wind {

    /**
     * The wind speed.
     */
    @JsonProperty("speed")
    private String speed;
}