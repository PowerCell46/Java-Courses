package com.investing.exception;

/**
 * Exception thrown when a metric calculation would result in division by zero.
 */
public class DivisionByZeroException extends InvalidMetricException {
    
    public DivisionByZeroException(String metricName) {
        super(String.format("Cannot calculate %s: Division by zero detected", metricName));
    }
}


