package com.springrest.client;

import com.springrest.model.WeatherResponse;


public interface WeatherClient {
	
	void evictCache();
    WeatherResponse fetchWeather(String city);
}