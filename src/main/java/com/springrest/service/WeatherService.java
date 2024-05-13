package com.springrest.service;

import org.springframework.http.ResponseEntity;

public interface WeatherService {
	
    ResponseEntity<?> getForecast(String city);
}
