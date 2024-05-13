package com.springrest.test.clientImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springrest.clientImpl.WeatherClientImpl;
import com.springrest.model.WeatherResponse;
import com.springrest.secrets.WeatherSecretsConfig;

@ExtendWith(SpringExtension.class)
public class WeatherClientImplTest {
	
	@Mock
    private WeatherSecretsConfig weatherSecretsConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherClientImpl weatherClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherClient = new WeatherClientImpl(weatherSecretsConfig, restTemplate);
    }

    @Test
    void fetchWeather_Success() throws JsonProcessingException {
        // Mock WeatherSecretsConfig
    
        when(weatherSecretsConfig.getApiKey()).thenReturn("your_api_key");

        // Mock RestTemplate
        RestTemplate restTemplateMock = mock(RestTemplate.class);

        // Mocking the responseEntity
        ResponseEntity<String> responseEntityMock = mock(ResponseEntity.class);

        // Mocking the behavior of restTemplate.getForEntity(...)
        when(restTemplateMock.getForEntity(anyString(), eq(String.class)))
            .thenReturn(responseEntityMock);

        // Sample response body
        String responseBody = "{\"cod\":\"200\",\"message\":0,\"cnt\":32,\"list\":[{\"dt\":1714942800,\"main\":{\"temp\":286.69,\"feels_like\":286.24,\"temp_min\":284.72,\"temp_max\":286.69,\"pressure\":1006,\"sea_level\":1006,\"grnd_level\":1004,\"humidity\":82,\"temp_kf\":1.97},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"brokenclouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":59},\"wind\":{\"speed\":1.59,\"deg\":150,\"gust\":2.09},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2024-05-0521:00:00\"}]}";

        // Mocking the response from the API
        when(responseEntityMock.getBody()).thenReturn(responseBody);
        when(responseEntityMock.getStatusCode()).thenReturn(HttpStatus.OK);

        // Create the WeatherClient instance
        WeatherClientImpl weatherClient = new WeatherClientImpl(weatherSecretsConfig, restTemplateMock);
        
        // Perform the test
        WeatherResponse weatherResponse = weatherClient.fetchWeather("London");

        // Verify
        assertNotNull(weatherResponse);
        assertNotNull(weatherResponse.getList());
        assertFalse(weatherResponse.getList().isEmpty());
        assertEquals("Clouds", weatherResponse.getList().get(0).getWeather().get(0).getMain());
        assertEquals("brokenclouds", weatherResponse.getList().get(0).getWeather().get(0).getDescription());
    }

    @Test
    void fetchWeather_Non200StatusCode() {
        // Mock WeatherSecretsConfig
        when(weatherSecretsConfig.getApiKey()).thenReturn("your_api_key");

        // Mock RestTemplate
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);

        // Perform the test
        WeatherResponse weatherResponse = weatherClient.fetchWeather("London");

        // Verify
        assertNull(weatherResponse);
    }

}
