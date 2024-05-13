package com.springrest.bean;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Represents temperature data with high and low temperatures and the date.
 */

@Data
@AllArgsConstructor
public class TemperatureData {

    /**
     * The high temperature.
     */
    private double highTemperature;

    /**
     * The low temperature.
     */
    private double lowTemperature;

    /**
     * The date associated with the temperature data.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;


}