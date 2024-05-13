package com.springrest.service;

import java.time.LocalDate;
import java.util.List;

import com.springrest.bean.PredictionData;
import com.springrest.model.TempData;

public interface WeatherPredictionStrategy {
	
	    List<PredictionData> predictConditions(List<TempData> weatherData, LocalDate targetDate);
}

