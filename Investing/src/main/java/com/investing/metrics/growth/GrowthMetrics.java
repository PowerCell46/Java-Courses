package com.investing.metrics.growth;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating growth-related financial metrics.
 * These metrics measure how fast a company is growing and its potential for future expansion.
 */
public final class GrowthMetrics {

    private GrowthMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Research and Development (R&D) spending as percentage of revenue.
     * Measures what portion of a company's revenue is spent on research and development.
     *
     * @param rAndD R&D spending
     * @param revenue total revenue
     * @return R&D spending as percentage of revenue (e.g., 8.5 for 8.5%)
     * @throws com.investing.exception.DivisionByZeroException if revenue is zero
     */
    public static double researchAndDevelopmentSpendingAsPercentageOfRevenue(double rAndD, double revenue) {
        ValidationUtils.validateNonZero(revenue, "R&D Spending as % of Revenue");
        return (rAndD / revenue) * 100;
    }

    /**
     * Calculates Revenue Growth rate.
     * Measures how much a company's total sales increase over a specific period (year-to-year/quarter-over-quarter).
     * Reflects whether the company is expanding its business and demand for its products or services is rising.
     *
     * @param currentPeriodRevenue revenue for the current period
     * @param previousPeriodRevenue revenue for the previous period
     * @return revenue growth as a percentage (e.g., 15.5 for 15.5% growth)
     * @throws com.investing.exception.DivisionByZeroException if previousPeriodRevenue is zero
     */
    public static double calculateRevenueGrowth(long currentPeriodRevenue, long previousPeriodRevenue) {
        ValidationUtils.validateNonZero(previousPeriodRevenue, "Revenue Growth");
        return ((double) (currentPeriodRevenue - previousPeriodRevenue) / previousPeriodRevenue) * 100;
    }

    /**
     * Calculates Earnings Per Share (EPS) Growth.
     * Measures how much a company's earnings per share increase over time, showing how efficiently it converts
     * profits into shareholder value.
     *
     * @param currentPeriodEPS EPS for the current period
     * @param previousPeriodEPS EPS for the previous period
     * @return EPS growth as a percentage (e.g., 20.0 for 20% growth)
     * @throws com.investing.exception.DivisionByZeroException if previousPeriodEPS is zero
     */
    public static double calculateEarningsPerShareGrowth(double currentPeriodEPS, double previousPeriodEPS) {
        ValidationUtils.validateNonZero(previousPeriodEPS, "EPS Growth");
        return ((currentPeriodEPS - previousPeriodEPS) / previousPeriodEPS) * 100;
    }
}


