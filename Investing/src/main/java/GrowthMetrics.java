public final class GrowthMetrics {

    public static double researchAndDevelopmentSpendingAsPercentageOfRevenue(double rAndD, double revenue) {
        // * Research and development (R&D) spending as % of revenue
        // measures what portion of a company’s revenue is spent on research and development

        return rAndD / revenue;
    }

    public static double sectorGrowthRate() {
        // * Sector growth rate
        // how fast the overall market or industry is expanding

        return 0;
    }

    public static double calculateRevenueGrowth(long currentPeriodRevenue, long previousPeriodRevenue) {
        // * Revenue growth
        // Measures how much a company's total sales increase over a specific period (year-to-year/quarter-over-quarter)
        // Reflects whether the company is expanding its business and demand for its products or services is rising.

        // ! Yahoo Finance: Financials → Income Statement → Total Revenue

        return ((double) (currentPeriodRevenue - previousPeriodRevenue) / previousPeriodRevenue) * 100;
    }

    public static double calculateEarningsPerShareGrowth(double currentPeriodEPS, double previousPeriodEPS) {
        // * Earnings per share (EPS) Growth
        // Measures how much a company's earnings per share increase over time, showing how efficiently it converts
        // profits into shareholder value.

        // ! Yahoo Finance: Financials → Income Statement → EPS (Diluted)

        return ((currentPeriodEPS - previousPeriodEPS) / previousPeriodEPS) * 100;
    }
}
