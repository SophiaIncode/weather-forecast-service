package com.springrest.serviceImpl;


import com.springrest.bean.PredictionData;
import com.springrest.model.TempData;
import com.springrest.service.WeatherPredictionStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SimpleWeatherPredictionStrategy implements WeatherPredictionStrategy{

	 	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleWeatherPredictionStrategy.class);
	 
	    public List<PredictionData> predictConditions(List<TempData> weatherData, LocalDate targetDate) {
	   
	    	
	    	if (CollectionUtils.isEmpty(weatherData)) {
	            LOGGER.error("Weather data list is null or empty");
	            throw new IllegalArgumentException("Weather data list is null or empty");
	        }
	        
	        // Check for null targetDate
	        if (targetDate == null) {
	            LOGGER.error("Target date is null");
	            throw new IllegalArgumentException("Target date is null");
	        }
	    	
	        List<PredictionData> predictions = new ArrayList<>();
	        for (TempData tempData : weatherData) {
	        	LOGGER.debug("Starting weather prediction for target date: {}", targetDate);
	        	try {
	            LocalDate recordDate = LocalDate.parse(tempData.getDt_txt().substring(0, 10));
	            if (recordDate.equals(targetDate)) {
	                PredictionData prediction = new PredictionData();
	                prediction.setTime(tempData.getDt_txt());

	                double windSpeed = Double.parseDouble(tempData.getWind().getSpeed());
	                if (windSpeed > 5) {
	                    prediction.setPrediction("It’s too windy, watch out!");
	                } else if (tempData.getTempDetails().getTemperature() > 40) {
	                    prediction.setPrediction("Use sunscreen lotion");
	                } else if (tempData.getWeather().stream().anyMatch(weather -> "Rain".equalsIgnoreCase(weather.getMain()))) {
	                    prediction.setPrediction("Carry umbrella");
	                } else {
	                    prediction.setPrediction("No special precautions needed");
	                }
	                predictions.add(prediction);
	            }
	        }
	       catch (Exception e){
	    	   LOGGER.error("Error occurred while predicting conditions: {}", e.getMessage());

	       }
	     }
	        LOGGER.debug("Weather prediction completed for target date: {}", targetDate);
	        return predictions;
	    }
	}
