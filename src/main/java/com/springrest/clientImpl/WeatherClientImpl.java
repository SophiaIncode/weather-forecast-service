package com.springrest.clientImpl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrest.client.WeatherClient;
import com.springrest.model.WeatherResponse;
import com.springrest.secrets.WeatherSecretsConfig;

@Service
@CacheConfig(cacheNames = "ForecastCache")
public class WeatherClientImpl implements WeatherClient {

	private final WeatherSecretsConfig weatherSecretsConfig;

	private final RestTemplate restTemplate;

	public WeatherClientImpl(WeatherSecretsConfig weatherSecretsConfig, RestTemplate restTemplate) {
		this.weatherSecretsConfig = weatherSecretsConfig;
		this.restTemplate = restTemplate;
	}

	@CacheEvict(value = "ForecastCache", allEntries = true)
	public void evictCache() {
		System.out.println("Clearing the cache");
	}

	@Override
	@Cacheable(value = "ForecastCache", key = "#city")
	public WeatherResponse fetchWeather(String city) {

		String api_url="https://api.openweathermap.org/data/2.5/forecast?q={CITY}&appid=API_KEY&cnt=32";
		String final_API = api_url.replace("{CITY}", city).replace("API_KEY",this.weatherSecretsConfig.getApiKey());
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(final_API, String.class);
		// Check the response status code
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			// Parse the response body into WeatherResponse
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(),
						WeatherResponse.class);
				return weatherResponse;
			} catch (JsonProcessingException e) {
				e.printStackTrace(); // Handle or log the exception
				return null;
			}
		} else {
			// Handle non-200 status code
			System.err.println("Error response from the API: " + responseEntity.getStatusCodeValue());
			return null;
		}

	}

}
