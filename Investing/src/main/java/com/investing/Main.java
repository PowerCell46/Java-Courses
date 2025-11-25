package com.investing;

import com.investing.model.FinancialData;
import com.investing.model.MetricResult;
import com.investing.service.MetricCalculatorService;

import java.util.List;

/**
 * Main class demonstrating the usage of the Investing Metrics Calculator.
 */
public class Main {
    public static void main(String[] args) {
        // Create sample financial data
        FinancialData companyData = createSampleFinancialData();

        // Create service instance
        MetricCalculatorService service = new MetricCalculatorService();

        // Calculate and display profitability metrics
        System.out.println("=== Profitability Metrics ===");
        List<MetricResult> profitabilityMetrics = service.calculateProfitabilityMetrics(companyData);
        profitabilityMetrics.forEach(System.out::println);

        System.out.println("\n=== Valuation Metrics ===");
        List<MetricResult> valuationMetrics = service.calculateValuationMetrics(companyData);
        valuationMetrics.forEach(System.out::println);

        System.out.println("\n=== Liquidity Metrics ===");
        List<MetricResult> liquidityMetrics = service.calculateLiquidityMetrics(companyData);
        liquidityMetrics.forEach(System.out::println);

        System.out.println("\n=== Leverage Metrics ===");
        List<MetricResult> leverageMetrics = service.calculateLeverageMetrics(companyData);
        leverageMetrics.forEach(System.out::println);

        // Example of individual metric calculation
        System.out.println("\n=== Individual Metric Example ===");
        double peRatio = com.investing.metrics.valuation.ValuationMetrics.calculatePriceToEarnings(
                companyData.getSharePrice(), companyData.getEarningsPerShare());
        System.out.printf("P/E Ratio: %.2f%n", peRatio);
    }

    /**
     * Creates sample financial data for demonstration purposes.
     *
     * @return sample FinancialData object
     */
    private static FinancialData createSampleFinancialData() {
        FinancialData data = new FinancialData();

        // Revenue and income
        data.setTotalRevenue(100_000_000L);
        data.setRevenue(100_000_000L);
        data.setCostOfRevenue(60_000_000L);
        data.setOperatingIncome(25_000_000L);
        data.setNetIncome(15_000_000L);
        data.setEBIT(25_000_000L);

        // Assets and liabilities
        data.setTotalAssets(150_000_000L);
        data.setCurrentAssets(50_000_000L);
        data.setCurrentLiabilities(30_000_000L);
        data.setTotalLiabilities(80_000_000L);
        data.setStockholdersEquity(70_000_000L);
        data.setShareholdersEquity(70_000_000L);
        data.setTotalDebt(50_000_000L);

        // Cash and equivalents
        data.setCashAndCashEquivalents(10_000_000L);
        data.setCashOnHand(8_000_000L);
        data.setShortTermInvestments(2_000_000L);
        data.setMarketableSecurities(5_000_000L);
        data.setAccountsReceivable(15_000_000L);

        // Shares and price
        data.setTotalSharesOutstanding(10_000_000L);
        data.setSharePrice(25.50);
        data.setEarningsPerShare(1.50);

        // Enterprise value
        data.setEnterpriseValue(280_000_000L);
        data.setEBITDA(30_000_000L);

        // Other metrics
        data.setInvestedCapital(120_000_000L);
        data.setNetOperatingProfitAfterTax(18_000_000L);
        data.setInterestExpense(2_000_000L);

        return data;
    }
}


