package com.springrest.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springrest.bean.ForecastResponse;
import com.springrest.bean.TemperatureData;
import com.springrest.client.WeatherClient;
import com.springrest.model.TempData;
import com.springrest.model.WeatherResponse;
import com.springrest.service.WeatherPredictionStrategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class WeatherDataMapperImpl implements WeatherDataMapper {

    private final WeatherPredictionStrategy predictionStrategy;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataMapperImpl.class);
    
    @Autowired
    public WeatherDataMapperImpl(WeatherPredictionStrategy predictionStrategy) {
        this.predictionStrategy = predictionStrategy;
    }
    private WeatherClient weatherClient;
    
    public WeatherDataMapperImpl()
    {
    	this.predictionStrategy=null;
    }

    public void setWeatherClient(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherDataMapperImpl(WeatherPredictionStrategy predictionStrategy, WeatherClient weatherClient) {
        this.predictionStrategy = predictionStrategy;
        this.weatherClient = weatherClient;
    }

    public ResponseEntity<ForecastResponse> mapToForecastResponse(WeatherResponse weatherResponse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        List<TempData> tempList = weatherResponse.getList();

        
        boolean todaySkipped = false;

        Map<LocalDate, List<TempData>> groupedByDateMap = new LinkedHashMap<>();
        try {
            groupedByDateMap = tempList.stream()
                    .collect(Collectors.groupingBy(
                            tempData -> LocalDate.parse(tempData.getDt_txt().substring(0, 10), formatter),
                            LinkedHashMap::new, // Preserve the order of insertion
                            Collectors.toList()
                    ));
        } catch (Exception e) {
            LOGGER.error("Error occurred while grouping weather data by date: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ForecastResponse forecastResponse = new ForecastResponse();
        List<TemperatureData> nextThreeDaysTemperatures = new ArrayList<>();

        try {
            for (Map.Entry<LocalDate, List<TempData>> entry : groupedByDateMap.entrySet()) {
                LocalDate date = entry.getKey();
                List<TempData> tempDataList = entry.getValue();

                // Skip today's temperatures
                if (!todaySkipped && date.equals(today)) {
                    todaySkipped = true;
                    continue;
                }

                // Skip if we have already processed temperatures for the next three days
                if (todaySkipped && date.isAfter(today.plusDays(3))) {
                    break;
                }

                double maxTemp = tempDataList.stream()
                        .mapToDouble(data -> data.getTempDetails().getTemp_max())
                        .max().orElse(Double.NaN);

                double minTemp = tempDataList.stream()
                        .mapToDouble(data -> data.getTempDetails().getTemp_min())
                        .min().orElse(Double.NaN);

                nextThreeDaysTemperatures.add(new TemperatureData(maxTemp, minTemp, date));
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing weather data: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Set the temperatures in the forecast response
        forecastResponse.setTemperatures(nextThreeDaysTemperatures);

        // Set the currentDate Prediction in the forecast response
        try {
            forecastResponse.setCurrentDatePrediction(predictionStrategy.predictConditions(tempList, today));
        } catch (Exception e) {
            LOGGER.error("Error occurred while predicting conditions for current date: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(forecastResponse, HttpStatus.OK);
    }
}