package com.springrest.test.cucumber;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

import com.springrest.controller.WeatherForecastController;
import com.springrest.service.WeatherService;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
public class WeatherForecastStepDefinitions {

	private String cityName;

	private ResponseEntity<?> responseEntity;

	private RestTemplate restTemplate = new RestTemplate();

	@Mock
	private WeatherService weatherService; // Removed the duplicate declaration

	private WeatherForecastController weatherForecastController;

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherForecastStepDefinitions.class);

	public WeatherForecastStepDefinitions() {
		// Initialize mock objects
		weatherService = Mockito.mock(WeatherService.class); // Initialize the mock
		weatherForecastController = new WeatherForecastController(weatherService); // Use the mocked service
	}

	@Given("a valid city name")
	public void givenValidCityName() {
		cityName = "London";
	}

	@When("the user requests weather forecast for the city")
	public void whenUserRequestsForecast() {
		Mockito.when(weatherService.getForecast(anyString())).thenReturn(ResponseEntity.ok().build());
		// Perform the actual request to the WeatherForecastController
		responseEntity = weatherForecastController.getForecast(cityName);
	}

	@Then("the system should return the forecast")
	public void thenSystemShouldReturnForecast() {
		Assert.assertNotNull(responseEntity);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Given("an invalid city name")
	public void givenInvalidCityName() {
		cityName = "12345";
		Mockito.when(weatherService.getForecast(cityName)).thenThrow(new IllegalArgumentException("Invalid city name")); 
	}

	@Then("the system should return an error message")
	public void thenSystemShouldReturnErrorMessage() {
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
	}

}
