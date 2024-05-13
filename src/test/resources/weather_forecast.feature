Feature: Weather Forecast
  As a user
  I want to get the weather forecast for next three days of the city and current date weather prediction
  So that I can plan my day accordingly

  Scenario: Valid city name
    Given a valid city name
    When the user requests weather forecast for the city
    Then the system should return the forecast

  Scenario: Invalid city name
    Given an invalid city name
    When the user requests weather forecast for the city
    Then the system should return an error message
