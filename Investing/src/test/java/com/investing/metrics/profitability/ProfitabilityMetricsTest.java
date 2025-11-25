package com.investing.metrics.profitability;

import com.investing.exception.DivisionByZeroException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfitabilityMetricsTest {

    @Test
    void testReturnOnInvestedCapital() {
        double roic = ProfitabilityMetrics.returnOnInvestedCapital(15_000_000L, 100_000_000L);
        assertThat(roic).isEqualTo(0.15);
    }

    @Test
    void testReturnOnInvestedCapital_ThrowsException_WhenInvestedCapitalIsZero() {
        assertThatThrownBy(() -> ProfitabilityMetrics.returnOnInvestedCapital(15_000_000L, 0L))
                .isInstanceOf(DivisionByZeroException.class)
                .hasMessageContaining("Return on Invested Capital");
    }

    @Test
    void testCalculateGrossMargin() {
        double grossMargin = ProfitabilityMetrics.calculateGrossMargin(100_000_000L, 60_000_000L);
        assertThat(grossMargin).isEqualTo(40.0);
    }

    @Test
    void testCalculateGrossMargin_ThrowsException_WhenRevenueIsZero() {
        assertThatThrownBy(() -> ProfitabilityMetrics.calculateGrossMargin(100_000_000L, 0L))
                .isInstanceOf(DivisionByZeroException.class);
    }

    @Test
    void testCalculateNetMargin() {
        double netMargin = ProfitabilityMetrics.calculateNetMargin(15_000_000L, 100_000_000L);
        assertThat(netMargin).isEqualTo(15.0);
    }

    @Test
    void testCalculateOperatingMargin() {
        double operatingMargin = ProfitabilityMetrics.calculateOperatingMargin(25_000_000L, 100_000_000L);
        assertThat(operatingMargin).isEqualTo(25.0);
    }

    @Test
    void testCalculateEarningsYield() {
        double earningsYield = ProfitabilityMetrics.calculateEarningsYield(1.50, 25.50);
        assertThat(earningsYield).isCloseTo(5.88, org.assertj.core.data.Offset.offset(0.01));
    }
}


