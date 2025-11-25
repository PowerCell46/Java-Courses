package com.investing.util;

import com.investing.exception.DivisionByZeroException;

/**
 * Utility class for validation operations used in metric calculations.
 */
public final class ValidationUtils {
    
    private ValidationUtils() {
        throw new AssertionError("Utility class");
    }
    
    /**
     * Validates that a divisor is not zero.
     * 
     * @param divisor the value to check
     * @param metricName the name of the metric being calculated
     * @throws DivisionByZeroException if divisor is zero
     */
    public static void validateNonZero(long divisor, String metricName) {
        if (divisor == 0) {
            throw new DivisionByZeroException(metricName);
        }
    }
    
    /**
     * Validates that a divisor is not zero.
     * 
     * @param divisor the value to check
     * @param metricName the name of the metric being calculated
     * @throws DivisionByZeroException if divisor is zero
     */
    public static void validateNonZero(double divisor, String metricName) {
        if (Math.abs(divisor) < 1e-10) {
            throw new DivisionByZeroException(metricName);
        }
    }
    
    /**
     * Validates that a value is not negative.
     * 
     * @param value the value to check
     * @param fieldName the name of the field
     * @throws IllegalArgumentException if value is negative
     */
    public static void validateNonNegative(long value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }
    
    /**
     * Validates that a value is not negative.
     * 
     * @param value the value to check
     * @param fieldName the name of the field
     * @throws IllegalArgumentException if value is negative
     */
    public static void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }
}


