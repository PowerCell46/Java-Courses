package com.investing.exception;

/**
 * Exception thrown when a metric calculation cannot be performed due to invalid input data.
 */
public class InvalidMetricException extends RuntimeException {
    
    public InvalidMetricException(String message) {
        super(message);
    }
    
    public InvalidMetricException(String message, Throwable cause) {
        super(message, cause);
    }
}


