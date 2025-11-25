package com.investing.metrics.risk;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating risk-related financial metrics.
 * These metrics measure various types of risk that a company faces.
 */
public final class RiskMetrics {

    private RiskMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Currency Exposure percentage.
     * Measures the risk a company faces due to fluctuations in foreign exchange rates on:
     * Revenue (foreign sales), Expenses (foreign purchases or operations),
     * Assets and liabilities denominated in foreign currencies.
     *
     * @param revenueInForeignCurrencies revenue in foreign currencies
     * @param totalRevenue total revenue
     * @return currency exposure as a percentage (e.g., 35.0 for 35%)
     * @throws com.investing.exception.DivisionByZeroException if totalRevenue is zero
     */
    public static double currencyExposure(long revenueInForeignCurrencies, long totalRevenue) {
        ValidationUtils.validateNonZero(totalRevenue, "Currency Exposure");
        return ((double) revenueInForeignCurrencies / totalRevenue) * 100;
    }
}


