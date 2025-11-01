public final class LiquidityMetrics {

    private LiquidityMetrics() {
        throw new AssertionError("Utility class");
    }

    public static double calculateCurrentRatio(long currentAssets, long currentLiabilities) {
        // * Current ratio
        // Measures a company's ability to meet its short-term obligations using its short-term assets.
        // It reflects liquidity - the firm's capacity to cover debts within a year without raising new capital.

        // <1.0 → the company may struggle to cover short-term debts.
        // 1.5–2.5 → considered healthy and efficient for most industries.
        // >3.0 → possibly too much idle capital or inventory buildup.

        // ! Yahoo finance: statistics: Current Ratio (mrq)

        return (double) currentAssets / currentLiabilities;
    }

    public static double calculateQuickRatio(
            long cashAndCashEquivalents,
            long marketableSecurities,
            long accountsReceivable,
            long currentLiabilities
    ) {
        // * Quick ratio (Acid-test ratio)
        // Evaluates whether a company can meet its short-term obligations with its most liquid assets.
        // Excludes inventory and prepaid expenses to focus on assets that can quickly convert to cash.

        long quickAssets = cashAndCashEquivalents + marketableSecurities + accountsReceivable;
        return (double) quickAssets / currentLiabilities;
    }
}
