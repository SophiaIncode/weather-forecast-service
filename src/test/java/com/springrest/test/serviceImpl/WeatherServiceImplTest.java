package com.springrest.test.serviceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import com.springrest.bean.ForecastResponse;
import com.springrest.client.WeatherClient;
import com.springrest.model.WeatherResponse;
import com.springrest.serviceImpl.WeatherDataMapper;
import com.springrest.serviceImpl.WeatherServiceImpl;

@ExtendWith(MockitoExtension.class)

public class WeatherServiceImplTest {

	@Mock
	private WeatherClient weatherClient;

	@Mock
	private WeatherDataMapper weatherDataMapper;

	@InjectMocks
	private WeatherServiceImpl weatherService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetForecast_CityFound() {
		// Arrange
		String city = "London";
		WeatherResponse mockWeatherResponse = new WeatherResponse();
		ResponseEntity<ForecastResponse> mockForecastResponseEntity = ResponseEntity.ok(new ForecastResponse());

		// Stubbing
		when(weatherClient.fetchWeather(city)).thenReturn(mockWeatherResponse);
		when(weatherDataMapper.mapToForecastResponse(mockWeatherResponse)).thenReturn(mockForecastResponseEntity);

		// Act
		ResponseEntity<?> responseEntity = weatherService.getForecast(city);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockForecastResponseEntity, responseEntity.getBody());
		verify(weatherClient, times(1)).fetchWeather(city);
		verify(weatherDataMapper, times(1)).mapToForecastResponse(mockWeatherResponse);
	}

	@Test
	public void testGetForecast_CityNotFound() {
		// Arrange
		String city = "NonexistentCity";
		when(weatherClient.fetchWeather(city)).thenThrow(HttpClientErrorException.NotFound.class);

        // Act
        ResponseEntity<?> responseEntity = weatherService.getForecast(city);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(weatherClient, times(1)).fetchWeather(city);
		// Assert
	}

	@Test
	public void testGetForecast_InternalServerError() {
		// Arrange
		String city = "London";

		// Stubbing
		when(weatherClient.fetchWeather(city)).thenThrow(RuntimeException.class);

		// Act
		ResponseEntity<?> responseEntity = weatherService.getForecast(city);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		verify(weatherClient, times(1)).fetchWeather(city);
	}

}
