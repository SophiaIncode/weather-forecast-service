package com.springrest.serviceImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.springrest.bean.ErrorInfo;
import com.springrest.bean.ForecastResponse;
import com.springrest.client.WeatherClient;
import com.springrest.model.WeatherResponse;
import com.springrest.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

	private WeatherClient weatherClient;
	private WeatherDataMapper weatherDataMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);

	@Autowired
	public WeatherServiceImpl(WeatherClient weatherClient, WeatherDataMapper weatherDataMapper) {
		this.weatherClient = weatherClient;
		this.weatherDataMapper = weatherDataMapper;
	}


	@Scheduled(fixedDelay = 60000)
	protected void scheduler() {
		this.weatherClient.evictCache();
	}

	@Override
	public ResponseEntity<?> getForecast(String city) {
		WeatherResponse weatherData;
		try {
			weatherData = weatherClient.fetchWeather(city);
		} catch (HttpClientErrorException.NotFound e) {
			LOGGER.error("City not found: {}", city);
			ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), "City not found: " + city,
					LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);// Return 404 if city is not found
		} catch (Exception e) {
			LOGGER.error("Error fetching weather data for city: {}", city, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		ResponseEntity<ForecastResponse> forecastResponse = weatherDataMapper.mapToForecastResponse(weatherData);
		return ResponseEntity.ok(forecastResponse);
	}

}