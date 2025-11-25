package com.investing.metrics.liquidity;

import com.investing.exception.DivisionByZeroException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LiquidityMetricsTest {

    @Test
    void testCalculateCurrentRatio() {
        double currentRatio = LiquidityMetrics.calculateCurrentRatio(50_000_000L, 30_000_000L);
        assertThat(currentRatio).isCloseTo(1.67, org.assertj.core.data.Offset.offset(0.01));
    }

    @Test
    void testCalculateCurrentRatio_ThrowsException_WhenLiabilitiesIsZero() {
        assertThatThrownBy(() -> LiquidityMetrics.calculateCurrentRatio(50_000_000L, 0L))
                .isInstanceOf(DivisionByZeroException.class)
                .hasMessageContaining("Current Ratio");
    }

    @Test
    void testCalculateQuickRatio() {
        double quickRatio = LiquidityMetrics.calculateQuickRatio(
                10_000_000L,  // cash and equivalents
                5_000_000L,   // marketable securities
                15_000_000L,  // accounts receivable
                30_000_000L   // current liabilities
        );
        assertThat(quickRatio).isEqualTo(1.0);
    }
}


