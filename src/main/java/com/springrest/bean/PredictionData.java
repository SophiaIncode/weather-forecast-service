package com.springrest.bean;

import lombok.Data;

/**
 * Represents prediction data with time and prediction information.
 */
@Data
public class PredictionData {

    /**
     * Time associated with the prediction data.
     */
    private String time;
    
    /**
     * The prediction information.
     */
    private String prediction;
    
}
