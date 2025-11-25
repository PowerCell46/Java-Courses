package com.investing.metrics.cashflow;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating cash flow-related financial metrics.
 * These metrics measure a company's ability to generate cash and manage its cash flow.
 */
public final class CashFlowMetrics {

    private CashFlowMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Operating Cash Flow.
     * Measures the actual cash generated from core business operations — before financing or investing activities.
     * Strong OCF means the company can fund operations, pay dividends, and invest in growth without taking on new debt.
     *
     * @param netIncome net income
     * @param nonCashExpenses non-cash expenses (depreciation, amortization, etc.)
     * @param changesInWorkingCapital changes in working capital
     * @return operating cash flow
     */
    public static long calculateOperatingCashFlow(long netIncome, long nonCashExpenses, long changesInWorkingCapital) {
        return netIncome + nonCashExpenses + changesInWorkingCapital;
    }

    /**
     * Calculates Capital Expenditures (CapEx).
     * Assesses how much a company invests in maintaining and expanding its asset base
     * (factories, equipment, R&D facilities, etc.).
     * CapEx represents cash spent on long-term assets — factories, machinery, vehicles, buildings, or technology infrastructure.
     *
     * @param capitalExpenditure capital expenditure amount
     * @return capital expenditures (typically a negative value in cash flow statements)
     */
    public static long calculateCapitalExpenditures(long capitalExpenditure) {
        return capitalExpenditure;
    }

    /**
     * Calculates Free Cash Flow (FCF).
     * Measures the actual cash a company generates after covering all its operating expenses and capital expenditures.
     * It's one of the most important indicators of a company's financial health, flexibility, and ability
     * to return value to shareholders (dividends, buybacks, reinvestment).
     *
     * @param operatingCashFlow operating cash flow
     * @param capitalExpenditure capital expenditure
     * @return free cash flow
     */
    public static long calculateFreeCashFlow(long operatingCashFlow, long capitalExpenditure) {
        return operatingCashFlow - capitalExpenditure;
    }

    /**
     * Calculates Price to Free Cash Flow (P/FCF) ratio.
     * Measures how much investors are paying for each dollar of free cash flow the company generates.
     * Lower P/FCF → potentially undervalued; higher → investors expect growth.
     *
     * @param freeCashFlow free cash flow
     * @param marketCap market capitalization
     * @return P/FCF ratio
     * @throws com.investing.exception.DivisionByZeroException if freeCashFlow is zero
     */
    public static double calculatePriceToFreeCashFlow(long freeCashFlow, long marketCap) {
        ValidationUtils.validateNonZero(freeCashFlow, "Price to Free Cash Flow");
        return (double) marketCap / freeCashFlow;
    }

    /**
     * Calculates Share Buyback Ratio.
     * Shows how much of its earnings a company uses to repurchase its own shares.
     * Indicates how aggressively a company is returning capital to shareholders by reducing share count.
     *
     * @param valueOfSharesRepurchased value of shares repurchased
     * @param netIncome net income
     * @return share buyback ratio (as decimal, e.g., 0.25 for 25%)
     * @throws com.investing.exception.DivisionByZeroException if netIncome is zero
     */
    public static double calculateShareBuybackRatio(long valueOfSharesRepurchased, long netIncome) {
        ValidationUtils.validateNonZero(netIncome, "Share Buyback Ratio");
        return (double) valueOfSharesRepurchased / netIncome;
    }

    /**
     * Calculates Dividend Yield.
     * The Dividend Yield measures the annual dividend income an investor earns relative to the stock's current market price.
     * It's expressed as a percentage and shows how much return you get from dividends alone.
     *
     * @param annualDividendsPerShare annual dividends per share
     * @param currentSharePrice current share price
     * @return dividend yield as a percentage (e.g., 3.5 for 3.5%)
     * @throws com.investing.exception.DivisionByZeroException if currentSharePrice is zero
     */
    public static double calculateDividendYield(double annualDividendsPerShare, double currentSharePrice) {
        ValidationUtils.validateNonZero(currentSharePrice, "Dividend Yield");
        return (annualDividendsPerShare / currentSharePrice) * 100;
    }

    /**
     * Calculates Dividend Payout Ratio.
     * Shows what percentage of a company's earnings are distributed to shareholders as dividends.
     * Indicates how sustainable the dividend is - lower values: the company retains more profits for growth;
     * higher values: it's returning more to shareholders.
     *
     * @param dividendsPerShare dividends per share
     * @param earningsPerShare earnings per share
     * @return dividend payout ratio as a percentage (e.g., 45.0 for 45%)
     * @throws com.investing.exception.DivisionByZeroException if earningsPerShare is zero
     */
    public static double calculateDividendPayoutRatio(double dividendsPerShare, double earningsPerShare) {
        ValidationUtils.validateNonZero(earningsPerShare, "Dividend Payout Ratio");
        return (dividendsPerShare / earningsPerShare) * 100;
    }
}


