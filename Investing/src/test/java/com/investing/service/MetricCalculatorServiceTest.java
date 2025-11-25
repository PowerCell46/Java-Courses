package com.investing.service;

import com.investing.model.FinancialData;
import com.investing.model.MetricResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MetricCalculatorServiceTest {

    private MetricCalculatorService service;
    private FinancialData sampleData;

    @BeforeEach
    void setUp() {
        service = new MetricCalculatorService();
        sampleData = createSampleData();
    }

    @Test
    void testCalculateProfitabilityMetrics() {
        List<MetricResult> results = service.calculateProfitabilityMetrics(sampleData);
        assertThat(results).isNotEmpty();
        assertThat(results).extracting(MetricResult::getMetricName)
                .contains("Gross Margin", "Net Margin", "Operating Margin");
    }

    @Test
    void testCalculateValuationMetrics() {
        List<MetricResult> results = service.calculateValuationMetrics(sampleData);
        assertThat(results).isNotEmpty();
        assertThat(results).extracting(MetricResult::getMetricName)
                .contains("Market Capitalization", "P/E Ratio", "P/B Ratio");
    }

    @Test
    void testCalculateLiquidityMetrics() {
        List<MetricResult> results = service.calculateLiquidityMetrics(sampleData);
        assertThat(results).isNotEmpty();
        assertThat(results).extracting(MetricResult::getMetricName)
                .contains("Current Ratio", "Quick Ratio");
    }

    @Test
    void testCalculateLeverageMetrics() {
        List<MetricResult> results = service.calculateLeverageMetrics(sampleData);
        assertThat(results).isNotEmpty();
        assertThat(results).extracting(MetricResult::getMetricName)
                .contains("Debt to Equity", "Interest Coverage");
    }

    @Test
    void testCalculateAllMetrics() {
        List<MetricResult> results = service.calculateAllMetrics(sampleData);
        assertThat(results).isNotEmpty();
        assertThat(results.size()).isGreaterThan(10);
    }

    private FinancialData createSampleData() {
        FinancialData data = new FinancialData();
        data.setTotalRevenue(100_000_000L);
        data.setRevenue(100_000_000L);
        data.setCostOfRevenue(60_000_000L);
        data.setOperatingIncome(25_000_000L);
        data.setNetIncome(15_000_000L);
        data.setEBIT(25_000_000L);
        data.setTotalAssets(150_000_000L);
        data.setCurrentAssets(50_000_000L);
        data.setCurrentLiabilities(30_000_000L);
        data.setStockholdersEquity(70_000_000L);
        data.setTotalDebt(50_000_000L);
        data.setCashAndCashEquivalents(10_000_000L);
        data.setMarketableSecurities(5_000_000L);
        data.setAccountsReceivable(15_000_000L);
        data.setTotalSharesOutstanding(10_000_000L);
        data.setSharePrice(25.50);
        data.setEarningsPerShare(1.50);
        data.setEnterpriseValue(280_000_000L);
        data.setEBITDA(30_000_000L);
        data.setInvestedCapital(120_000_000L);
        data.setNetOperatingProfitAfterTax(18_000_000L);
        data.setInterestExpense(2_000_000L);
        return data;
    }
}


