public final class RiskMetrics {

    public static double beta() {
        // * Beta (β)
        // how much a stock’s price moves relative to the overall market

        // ! Yahoo finance: statistics: Beta (5Y Monthly)

        return 0;
    }

    public static double currencyExposure(long revenueInForeignCurrencies, long totalRevenue) {
        // * Currency exposure
        // measures the risk a company faces due to fluctuations in foreign exchange rates on:
        // Revenue (foreign sales), Expenses (foreign purchases or operations), Assets and liabilities denominated in foreign currencies

        return ((double) revenueInForeignCurrencies / totalRevenue) * 100;
    }

    public static double geographicDiversification() {
        // * Geographic diversification
        // measures how much a company’s revenue, assets, or operations are spread across multiple countries or regions
        // High diversification → less risk from a single country’s economic or regulatory changes.
        // Low diversification → more vulnerable to local events (currency, trade policies, taxes).

        return 0;
    }
}
