package com.investing.metrics.efficiency;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating management efficiency-related financial metrics.
 * These metrics measure how well a company's management uses its resources to generate profits and returns.
 */
public final class ManagementEfficiency {

    private ManagementEfficiency() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Return on Assets (ROA).
     * Measures how efficiently a company uses its total assets to generate profit.
     *
     * @param netIncome net income
     * @param totalAssets total assets
     * @return return on assets as a decimal (e.g., 0.12 for 12%)
     * @throws com.investing.exception.DivisionByZeroException if totalAssets is zero
     */
    public static double returnOnAssets(long netIncome, long totalAssets) {
        ValidationUtils.validateNonZero(totalAssets, "Return on Assets");
        return (double) netIncome / totalAssets;
    }

    /**
     * Calculates Return on Equity (ROE).
     * Measures how effectively a company generates profit from its shareholders' invested capital.
     * It reflects management: higher → company is using its equity to produce strong returns for investors.
     *
     * @param netIncome net income
     * @param shareholdersEquity shareholders' equity
     * @return return on equity as a percentage (e.g., 18.5 for 18.5%)
     * @throws com.investing.exception.DivisionByZeroException if shareholdersEquity is zero
     */
    public static double returnOnEquity(long netIncome, long shareholdersEquity) {
        ValidationUtils.validateNonZero(shareholdersEquity, "Return on Equity");
        return ((double) netIncome / shareholdersEquity) * 100;
    }

    /**
     * Calculates Asset Turnover Ratio.
     * Measures how efficiently a company uses its assets to generate revenue.
     * Higher ratio → company uses assets efficiently to produce revenue.
     * Lower ratio → assets may be underutilized or the business is capital-intensive.
     *
     * @param totalRevenue total revenue
     * @param totalAssets total assets
     * @return asset turnover ratio
     * @throws com.investing.exception.DivisionByZeroException if totalAssets is zero
     */
    public static double assetTurnoverRatio(double totalRevenue, double totalAssets) {
        ValidationUtils.validateNonZero(totalAssets, "Asset Turnover Ratio");
        return totalRevenue / totalAssets;
    }

    /**
     * Calculates Inventory Turnover Ratio.
     * Measures how quickly a company sells and replaces its inventory.
     *
     * @param costOfGoodsSold cost of goods sold
     * @param inventory inventory value
     * @return inventory turnover ratio
     * @throws com.investing.exception.DivisionByZeroException if inventory is zero
     */
    public static double inventoryTurnoverRatio(double costOfGoodsSold, double inventory) {
        ValidationUtils.validateNonZero(inventory, "Inventory Turnover Ratio");
        return costOfGoodsSold / inventory;
    }
}


