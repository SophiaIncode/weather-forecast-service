FROM openjdk:8
EXPOSE 8080
ADD target/weather-forecast-devops.jar weather-forecast-devops.jar
# Set the environment variable
ENV MY_API_KEY=/run/secrets/Api-Key
ENTRYPOINT ["java","-jar","/weather-forecast-devops.jar"]