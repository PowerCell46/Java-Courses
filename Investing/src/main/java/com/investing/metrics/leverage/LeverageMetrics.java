package com.investing.metrics.leverage;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating leverage-related financial metrics.
 * These metrics measure a company's use of debt and its ability to meet debt obligations.
 */
public final class LeverageMetrics {

    private LeverageMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Debt to Equity ratio (D/E).
     * Shows how much a company relies on borrowed money/debt vs shareholder's capital (equity) to finance its operations.
     * Higher ratios: greater dependence on debt and potentially higher risk.
     *
     * @param totalDebt total debt
     * @param stockholdersEquity stockholders' equity
     * @return debt to equity ratio
     * @throws com.investing.exception.DivisionByZeroException if stockholdersEquity is zero
     */
    public static double calculateTotalDebtEquity(long totalDebt, long stockholdersEquity) {
        ValidationUtils.validateNonZero(stockholdersEquity, "Debt to Equity");
        return (double) totalDebt / stockholdersEquity;
    }

    /**
     * Calculates Interest Coverage Ratio.
     * Measures how easily a company can pay interest on its outstanding debt using its operating profits.
     * It's a key indicator of financial stability and solvency, showing how safe the firm's debt load is relative
     * to its earnings power.
     *
     * @param EBIT earnings before interest and taxes
     * @param interestExpense interest expense
     * @return interest coverage ratio
     * @throws com.investing.exception.DivisionByZeroException if interestExpense is zero
     */
    public static double calculateInterestCoverageRatio(long EBIT, long interestExpense) {
        ValidationUtils.validateNonZero(interestExpense, "Interest Coverage Ratio");
        return (double) EBIT / interestExpense;
    }

    /**
     * Calculates Total Liabilities.
     * Represents all debts and financial obligations a company owes to external parties.
     *
     * @param currentLiabilities current liabilities
     * @param nonCurrentLiabilities non-current liabilities
     * @return total liabilities
     */
    public static long calculateTotalLiabilities(long currentLiabilities, long nonCurrentLiabilities) {
        return currentLiabilities + nonCurrentLiabilities;
    }
}


