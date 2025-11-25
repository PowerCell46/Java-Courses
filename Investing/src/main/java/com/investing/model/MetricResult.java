package com.investing.model;

import java.time.LocalDateTime;

/**
 * Represents the result of a metric calculation with metadata.
 */
public class MetricResult {
    private final String metricName;
    private final double value;
    private final LocalDateTime timestamp;
    private final String unit;
    
    public MetricResult(String metricName, double value, String unit) {
        this.metricName = metricName;
        this.value = value;
        this.unit = unit;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getMetricName() {
        return metricName;
    }
    
    public double getValue() {
        return value;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getUnit() {
        return unit;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %.2f %s (calculated at %s)", metricName, value, unit, timestamp);
    }
}


