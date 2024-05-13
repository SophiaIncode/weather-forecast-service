package com.springrest.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import com.springrest.bean.ForecastResponse;
import com.springrest.controller.WeatherForecastController;
import com.springrest.service.WeatherService;

public class WeatherForecastControllerTest {

	@Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherForecastController controller;

	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetForecast_Success() {
        // Mocking the behavior of weatherService.getForecast()
        String cityName = "London";
        String jsonResponse = "{\"headers\":{},\"body\":{\"temperatures\":[{\"highTemperature\":291.07,\"lowTemperature\":282.5,\"date\":\"2024-05-06\"},{\"highTemperature\":289.56,\"lowTemperature\":282.34,\"date\":\"2024-05-07\"},{\"highTemperature\":291.53,\"lowTemperature\":283.3,\"date\":\"2024-05-08\"}],\"currentDatePrediction\":[{\"time\":\"2024-05-0518:00:00\",\"prediction\":\"Carryumbrella\"},{\"time\":\"2024-05-0521:00:00\",\"prediction\":\"Nospecialprecautionsneeded\"}]},\"statusCodeValue\":200,\"statusCode\":\"OK\"}";
        ResponseEntity<?> responseEntity = ResponseEntity.ok(jsonResponse);

        Mockito.doReturn((ResponseEntity<ForecastResponse>) responseEntity)
        .when(weatherService)
        .getForecast(Mockito.anyString());


        // Calling the controller method
        ResponseEntity<?> response = controller.getForecast(cityName);

        // Verifying the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jsonResponse, response.getBody());
    }

    @Test
    public void testGetForecast_MissingCity() {
        // Calling the controller method without providing the city parameter
        ResponseEntity<?> response = controller.getForecast(null);

        // Verifying the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("City name is required"));
    }

    @Test
    public void testGetForecast_InvalidCityFormat() {
        // Providing an invalid city name format
        String invalidCityName = "123City";

        // Calling the controller method with an invalid city name
        ResponseEntity<?> response = controller.getForecast(invalidCityName);

        // Verifying the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Invalid city name format"));
    }
}
