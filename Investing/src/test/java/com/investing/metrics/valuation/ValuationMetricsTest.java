package com.investing.metrics.valuation;

import com.investing.exception.DivisionByZeroException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValuationMetricsTest {

    @Test
    void testCalculatePriceToEarnings() {
        double peRatio = ValuationMetrics.calculatePriceToEarnings(25.50, 1.50);
        assertThat(peRatio).isEqualTo(17.0);
    }

    @Test
    void testCalculatePriceToEarnings_ThrowsException_WhenEarningsPerShareIsZero() {
        assertThatThrownBy(() -> ValuationMetrics.calculatePriceToEarnings(25.50, 0.0))
                .isInstanceOf(DivisionByZeroException.class)
                .hasMessageContaining("Price to Earnings");
    }

    @Test
    void testCalculateMarketCapitalization() {
        double marketCap = ValuationMetrics.calculateMarketCapitalization(25.50, 10_000_000L);
        assertThat(marketCap).isEqualTo(255_000_000.0);
    }

    @Test
    void testCalculateEnterpriseValue() {
        long ev = ValuationMetrics.calculateEnterpriseValue(
                250_000_000L,  // market cap
                10_000_000L,   // short term debt
                40_000_000L,   // long term debt
                0L,            // preferred stock
                0L,            // minority interest
                8_000_000L,    // cash on hand
                2_000_000L     // short term investments
        );
        assertThat(ev).isEqualTo(290_000_000L);
    }

    @Test
    void testCalculateBookValuePerShare() {
        double bvps = ValuationMetrics.calculateBookValuePerShare(
                150_000_000L,  // total assets
                80_000_000L,   // total liabilities
                0,             // preferred equity
                10_000_000L    // total outstanding shares
        );
        assertThat(bvps).isEqualTo(7.0);
    }

    @Test
    void testCalculatePriceToBooks() {
        double pbRatio = ValuationMetrics.calculatePriceToBooks(
                25.50,         // share price
                70_000_000L,   // stockholders equity
                10_000_000L    // total shares outstanding
        );
        assertThat(pbRatio).isCloseTo(3.64, org.assertj.core.data.Offset.offset(0.01));
    }
}


