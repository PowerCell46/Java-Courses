package com.investing.service;

import com.investing.model.FinancialData;
import com.investing.model.MetricResult;
import com.investing.metrics.leverage.LeverageMetrics;
import com.investing.metrics.liquidity.LiquidityMetrics;
import com.investing.metrics.profitability.ProfitabilityMetrics;
import com.investing.metrics.valuation.ValuationMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class that orchestrates metric calculations using financial data.
 * Provides a high-level interface for calculating multiple metrics at once.
 */
public class MetricCalculatorService {

    /**
     * Calculates all available profitability metrics for the given financial data.
     *
     * @param data financial data
     * @return list of metric results
     */
    public List<MetricResult> calculateProfitabilityMetrics(FinancialData data) {
        List<MetricResult> results = new ArrayList<>();

        if (data.getInvestedCapital() > 0 && data.getNetOperatingProfitAfterTax() != 0) {
            double roic = ProfitabilityMetrics.returnOnInvestedCapital(
                    data.getNetOperatingProfitAfterTax(), data.getInvestedCapital());
            results.add(new MetricResult("ROIC", roic, "decimal"));
        }

        if (data.getTotalRevenue() > 0) {
            double grossMargin = ProfitabilityMetrics.calculateGrossMargin(
                    data.getTotalRevenue(), data.getCostOfRevenue());
            results.add(new MetricResult("Gross Margin", grossMargin, "%"));

            double netMargin = ProfitabilityMetrics.calculateNetMargin(
                    data.getNetIncome(), data.getRevenue());
            results.add(new MetricResult("Net Margin", netMargin, "%"));

            double operatingMargin = ProfitabilityMetrics.calculateOperatingMargin(
                    data.getOperatingIncome(), data.getRevenue());
            results.add(new MetricResult("Operating Margin", operatingMargin, "%"));
        }

        if (data.getSharePrice() > 0 && data.getEarningsPerShare() != 0) {
            double earningsYield = ProfitabilityMetrics.calculateEarningsYield(
                    data.getEarningsPerShare(), data.getSharePrice());
            results.add(new MetricResult("Earnings Yield", earningsYield, "%"));
        }

        return results;
    }

    /**
     * Calculates all available valuation metrics for the given financial data.
     *
     * @param data financial data
     * @return list of metric results
     */
    public List<MetricResult> calculateValuationMetrics(FinancialData data) {
        List<MetricResult> results = new ArrayList<>();

        if (data.getEnterpriseValue() > 0 && data.getTotalRevenue() > 0) {
            double evToRevenue = ValuationMetrics.calculateEnterpriseValueToRevenue(
                    data.getEnterpriseValue(), data.getTotalRevenue());
            results.add(new MetricResult("EV/Revenue", evToRevenue, "ratio"));

            if (data.getEBITDA() > 0) {
                double evToEbitda = ValuationMetrics.calculateEnterpriseValueToEBITDA(
                        data.getEnterpriseValue(), data.getEBITDA());
                results.add(new MetricResult("EV/EBITDA", evToEbitda, "ratio"));
            }
        }

        if (data.getSharePrice() > 0 && data.getTotalSharesOutstanding() > 0) {
            double marketCap = ValuationMetrics.calculateMarketCapitalization(
                    data.getSharePrice(), data.getTotalSharesOutstanding());
            results.add(new MetricResult("Market Capitalization", marketCap, "currency"));

            if (data.getEarningsPerShare() > 0) {
                double peRatio = ValuationMetrics.calculatePriceToEarnings(
                        data.getSharePrice(), data.getEarningsPerShare());
                results.add(new MetricResult("P/E Ratio", peRatio, "ratio"));
            }

            if (data.getStockholdersEquity() > 0) {
                double pbRatio = ValuationMetrics.calculatePriceToBooks(
                        data.getSharePrice(), data.getStockholdersEquity(), data.getTotalSharesOutstanding());
                results.add(new MetricResult("P/B Ratio", pbRatio, "ratio"));
            }
        }

        return results;
    }

    /**
     * Calculates liquidity metrics for the given financial data.
     *
     * @param data financial data
     * @return list of metric results
     */
    public List<MetricResult> calculateLiquidityMetrics(FinancialData data) {
        List<MetricResult> results = new ArrayList<>();

        if (data.getCurrentLiabilities() > 0) {
            double currentRatio = LiquidityMetrics.calculateCurrentRatio(
                    data.getCurrentAssets(), data.getCurrentLiabilities());
            results.add(new MetricResult("Current Ratio", currentRatio, "ratio"));

            double quickRatio = LiquidityMetrics.calculateQuickRatio(
                    data.getCashAndCashEquivalents(),
                    data.getMarketableSecurities(),
                    data.getAccountsReceivable(),
                    data.getCurrentLiabilities());
            results.add(new MetricResult("Quick Ratio", quickRatio, "ratio"));
        }

        return results;
    }

    /**
     * Calculates leverage metrics for the given financial data.
     *
     * @param data financial data
     * @return list of metric results
     */
    public List<MetricResult> calculateLeverageMetrics(FinancialData data) {
        List<MetricResult> results = new ArrayList<>();

        if (data.getStockholdersEquity() > 0) {
            double debtToEquity = LeverageMetrics.calculateTotalDebtEquity(
                    data.getTotalDebt(), data.getStockholdersEquity());
            results.add(new MetricResult("Debt to Equity", debtToEquity, "ratio"));
        }

        if (data.getInterestExpense() > 0) {
            double interestCoverage = LeverageMetrics.calculateInterestCoverageRatio(
                    data.getEBIT(), data.getInterestExpense());
            results.add(new MetricResult("Interest Coverage", interestCoverage, "ratio"));
        }

        return results;
    }

    /**
     * Calculates all available metrics for the given financial data.
     *
     * @param data financial data
     * @return list of all metric results
     */
    public List<MetricResult> calculateAllMetrics(FinancialData data) {
        List<MetricResult> allResults = new ArrayList<>();
        allResults.addAll(calculateProfitabilityMetrics(data));
        allResults.addAll(calculateValuationMetrics(data));
        allResults.addAll(calculateLiquidityMetrics(data));
        allResults.addAll(calculateLeverageMetrics(data));
        return allResults;
    }
}

