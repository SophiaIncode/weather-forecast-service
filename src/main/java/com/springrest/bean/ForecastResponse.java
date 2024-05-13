package com.springrest.bean;

import java.util.List;

import lombok.Data;
/**
 * The {@code ForecastResponse} class represents the response data structure for a weather forecast.
 * It contains a list of temperature data and a list of prediction data for the current date.
 */
@Data
public class ForecastResponse {

    /**
     * The list of temperature data for the forecast.
     */
    private List<TemperatureData> temperatures;

    /**
     * The list of prediction data for the current date.
     */
    private List<PredictionData> currentDatePrediction;
}