package com.investing.metrics.liquidity;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating liquidity-related financial metrics.
 * These metrics measure a company's ability to meet its short-term obligations.
 */
public final class LiquidityMetrics {

    private LiquidityMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Current Ratio.
     * Measures a company's ability to meet its short-term obligations using its short-term assets.
     * It reflects liquidity - the firm's capacity to cover debts within a year without raising new capital.
     * <1.0 → the company may struggle to cover short-term debts.
     * 1.5–2.5 → considered healthy and efficient for most industries.
     * >3.0 → possibly too much idle capital or inventory buildup.
     *
     * @param currentAssets current assets
     * @param currentLiabilities current liabilities
     * @return current ratio
     * @throws com.investing.exception.DivisionByZeroException if currentLiabilities is zero
     */
    public static double calculateCurrentRatio(long currentAssets, long currentLiabilities) {
        ValidationUtils.validateNonZero(currentLiabilities, "Current Ratio");
        return (double) currentAssets / currentLiabilities;
    }

    /**
     * Calculates Quick Ratio (Acid-test ratio).
     * Evaluates whether a company can meet its short-term obligations with its most liquid assets.
     * Excludes inventory and prepaid expenses to focus on assets that can quickly convert to cash.
     *
     * @param cashAndCashEquivalents cash and cash equivalents
     * @param marketableSecurities marketable securities
     * @param accountsReceivable accounts receivable
     * @param currentLiabilities current liabilities
     * @return quick ratio
     * @throws com.investing.exception.DivisionByZeroException if currentLiabilities is zero
     */
    public static double calculateQuickRatio(
            long cashAndCashEquivalents,
            long marketableSecurities,
            long accountsReceivable,
            long currentLiabilities
    ) {
        ValidationUtils.validateNonZero(currentLiabilities, "Quick Ratio");
        long quickAssets = cashAndCashEquivalents + marketableSecurities + accountsReceivable;
        return (double) quickAssets / currentLiabilities;
    }
}


