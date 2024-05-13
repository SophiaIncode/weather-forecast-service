package com.springrest.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
/**
 * This class represents information about an error, including its status code, error message, and timestamp.
 */
@Data
public class ErrorInfo {

    private int statusCode; // The status code associated with the error
    private String errorMessage; // The error message describing the error
    private String timestamp; // The timestamp when the error occurred

    /**
     * Constructs an ErrorInfo object with the specified status code, error message, and timestamp.
     *
     * @param statusCode   The status code associated with the error
     * @param errorMessage The error message describing the error
     * @param timestamp    The timestamp when the error occurred
     */
    public ErrorInfo(int statusCode, String errorMessage, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Gets the status code associated with the error.
     *
     * @return The status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code associated with the error.
     *
     * @param statusCode The status code to set
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets the error message describing the error.
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message describing the error.
     *
     * @param errorMessage The error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the timestamp when the error occurred.
     *
     * @return The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     *
     * @param timestamp The timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns a string representation of the ErrorInfo object.
     *
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return "ErrorInfo [statusCode=" + statusCode + ", errorMessage=" + errorMessage + ", timestamp=" + timestamp
                + "]";
    }
}