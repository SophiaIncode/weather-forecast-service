package com.springrest.secrets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

/**
 * Configuration class for OpenWeather API secrets including URL and API key.
 */
@Component
public class WeatherSecretsConfig {
	/**
     * Retrieves the API key from the secret file and removes surrounding double quotes.
     *
     * @return The API key.
     */
    public String getApiKey() {
        StringBuilder apiKey = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(System.getenv("MY_API_KEY")))) {
            String line;
            while ((line = br.readLine()) != null) {
                apiKey.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }

        // Remove surrounding double quotes, if present
        String apiKeyValue = apiKey.toString().replaceAll("^\"|\"$", "");
        return apiKeyValue.trim();
    }


}
