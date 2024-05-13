package com.springrest.controller;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.springrest.bean.ErrorInfo;
import com.springrest.service.WeatherService;

@RestController
@EnableSwagger2
public class WeatherForecastController {

	private final WeatherService weatherService;

	@Autowired
	public WeatherForecastController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherForecastController.class);

	@GetMapping(value = "/forecast", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getForecast(@RequestParam String city) {
		if (city == null || city.isEmpty()) {
            ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "City name is required", LocalDateTime.now());
            return ResponseEntity.badRequest().body(errorInfo);
        }

        // Check if city name matches a specific regex pattern
        String cityRegex = "^[a-zA-Z]+$"; // Example regex pattern allowing only letters
        if (!Pattern.matches(cityRegex, city)) {
            ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Invalid city name format", LocalDateTime.now());
            return ResponseEntity.badRequest().body(errorInfo);
        }
		LOGGER.info("Fetching forecast for city: {}", city);
		try {
			return weatherService.getForecast(city);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Error fetching forecast for city: {}", city, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}
