package com.springrest.serviceImpl;

import org.springframework.http.ResponseEntity;
import com.springrest.bean.ForecastResponse;
import com.springrest.model.WeatherResponse;

public interface WeatherDataMapper {
	
	ResponseEntity<ForecastResponse> mapToForecastResponse(WeatherResponse weatherData);

}
