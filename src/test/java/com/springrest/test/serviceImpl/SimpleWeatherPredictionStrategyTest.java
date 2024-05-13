package com.springrest.test.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrest.bean.PredictionData;
import com.springrest.model.TempData;
import com.springrest.serviceImpl.SimpleWeatherPredictionStrategy;

@ExtendWith(SpringExtension.class)
public class SimpleWeatherPredictionStrategyTest {
	
	@Mock
    private TempData tempDataMock;

    @InjectMocks
    private SimpleWeatherPredictionStrategy weatherPredictionStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
    void testPredictConditions() throws IOException {
        // Given
        String json = "[{\"dt\":1714942800,\"main\":{\"temp\":286.69,\"feels_like\":286.24,\"temp_min\":284.72,\"temp_max\":286.69,\"pressure\":1006,\"sea_level\":1006,\"grnd_level\":1004,\"humidity\":82,\"temp_kf\":1.97},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"brokenclouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":59},\"wind\":{\"speed\":1.59,\"deg\":150,\"gust\":2.09},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2024-05-0521:00:00\"},{\"dt\":1714953600,\"main\":{\"temp\":285.62,\"feels_like\":285.22,\"temp_min\":284.59,\"temp_max\":285.62,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":1004,\"humidity\":88,\"temp_kf\":1.03},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"brokenclouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":77},\"wind\":{\"speed\":0.74,\"deg\":187,\"gust\":0.87},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2024-05-0600:00:00\"},{\"dt\":1714964400,\"main\":{\"temp\":283.9,\"feels_like\":283.49,\"temp_min\":283.9,\"temp_max\":283.9,\"pressure\":1006,\"sea_level\":1006,\"grnd_level\":1003,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcastclouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":0.68,\"deg\":266,\"gust\":0.78},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2024-05-0603:00:00\"},{\"dt\":1714975200,\"main\":{\"temp\":284.28,\"feels_like\":283.85,\"temp_min\":284.28,\"temp_max\":284.28,\"pressure\":1006,\"sea_level\":1006,\"grnd_level\":1003,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcastclouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":0.49,\"deg\":66,\"gust\":1.1},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2024-05-0606:00:00\"},{\"dt\":1714986000,\"main\":{\"temp\":288.51,\"feels_like\":287.9,\"temp_min\":288.51,\"temp_max\":288.51,\"pressure\":1005,\"sea_level\":1005,\"grnd_level\":1003,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcastclouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":1.79,\"deg\":103,\"gust\":2.11},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2024-05-0609:00:00\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<TempData> weatherData = objectMapper.readValue(json, new TypeReference<List<TempData>>(){});
        assertNotNull(weatherData);
        LocalDate targetDate = LocalDate.parse("2024-05-06"); // Choose target date accordingly

        // When
        SimpleWeatherPredictionStrategy predictionStrategy = new SimpleWeatherPredictionStrategy();
        List<PredictionData> predictions = predictionStrategy.predictConditions(weatherData, targetDate);
        
        assertNotNull(predictions);
    }

}
