package com.investing.metrics.profitability;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating profitability-related financial metrics.
 * These metrics measure a company's ability to generate profit from its operations.
 */
public final class ProfitabilityMetrics {

    private ProfitabilityMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Return on Invested Capital (ROIC).
     * Measures the return a company earns on all capital invested.
     * For every unit of capital invested, how much profit does management generate after taxes?
     *
     * @param netOperatingProfitAfterTax the net operating profit after tax
     * @param investedCapital the total invested capital
     * @return ROIC as a decimal (e.g., 0.15 for 15%)
     * @throws com.investing.exception.DivisionByZeroException if investedCapital is zero
     */
    public static double returnOnInvestedCapital(long netOperatingProfitAfterTax, long investedCapital) {
        ValidationUtils.validateNonZero(investedCapital, "Return on Invested Capital");
        return (double) netOperatingProfitAfterTax / investedCapital;
    }

    /**
     * Calculates Gross Margin percentage.
     * Measures how much profit a company keeps from its revenue after accounting for the direct costs
     * of producing goods or services. It reflects pricing power, production efficiency, and competitive advantage.
     *
     * @param totalRevenue total revenue
     * @param costOfRevenue cost of revenue
     * @return gross margin as a percentage (e.g., 35.5 for 35.5%)
     * @throws com.investing.exception.DivisionByZeroException if totalRevenue is zero
     */
    public static double calculateGrossMargin(long totalRevenue, long costOfRevenue) {
        ValidationUtils.validateNonZero(totalRevenue, "Gross Margin");
        return ((double) (totalRevenue - costOfRevenue) / totalRevenue) * 100;
    }

    /**
     * Calculates Net Margin percentage.
     * Measures how much of a company's revenue turns into profit - showing efficiency, pricing power, and cost control.
     * Net margin: what remains after all expenses, interest, and taxes.
     *
     * @param netIncome net income
     * @param revenue total revenue
     * @return net margin as a percentage (e.g., 12.5 for 12.5%)
     * @throws com.investing.exception.DivisionByZeroException if revenue is zero
     */
    public static double calculateNetMargin(long netIncome, long revenue) {
        ValidationUtils.validateNonZero(revenue, "Net Margin");
        return ((double) netIncome / revenue) * 100;
    }

    /**
     * Calculates Operating Margin percentage.
     * Measures how much of a company's revenue turns into profit - showing efficiency, pricing power, and cost control.
     * Operating margin: how much profit the core business generates before interest and taxes.
     *
     * @param operatingIncome operating income
     * @param revenue total revenue
     * @return operating margin as a percentage (e.g., 18.3 for 18.3%)
     * @throws com.investing.exception.DivisionByZeroException if revenue is zero
     */
    public static double calculateOperatingMargin(long operatingIncome, long revenue) {
        ValidationUtils.validateNonZero(revenue, "Operating Margin");
        return ((double) operatingIncome / revenue) * 100;
    }

    /**
     * Calculates Earnings Yield.
     * Shows how much earnings a company generates per 1 dollar of its stock price.
     * Higher earnings yield: the stock might be undervalued.
     * Lower earnings yield: the stock might be expensive or investors expect high future growth.
     *
     * @param earningsPerShare earnings per share
     * @param sharePrice current share price
     * @return earnings yield as a percentage (e.g., 5.2 for 5.2%)
     * @throws com.investing.exception.DivisionByZeroException if sharePrice is zero
     */
    public static double calculateEarningsYield(double earningsPerShare, double sharePrice) {
        ValidationUtils.validateNonZero(sharePrice, "Earnings Yield");
        return (earningsPerShare / sharePrice) * 100;
    }
}


