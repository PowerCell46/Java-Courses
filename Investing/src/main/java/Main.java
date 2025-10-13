public class Main {
    public static void main(String[] args) {
//        long currentPeriodRevenue = 311_938_000;
//        long previousPeriodRevenue = 290_403_000;
//        System.out.println(calculateRevenueGrowth(currentPeriodRevenue, previousPeriodRevenue));

//        double currentPeriodEPS = 24.95;
//        double previousPeriodEPS = 22.63;
//        System.out.println(calculateEarningsPerShareGrowth(currentPeriodEPS, previousPeriodEPS));

//        long netIncome = 111_068_000;
//        long stockholdersEquity = 143_486_000;
//        System.out.println(calculateReturnOnEquity(netIncome, stockholdersEquity));
    }

    public static double calculateReturnOnEquity(long netIncome, long shareholdersEquity) {
        // * Return on equity (ROE)
        // Equity: Собствен капитал
        // Measures how effectively a company generates profit from its shareholders' invested capital.
        // It reflects management: higher -> company is using its equity to produce strong returns for investors.

        // ! Yahoo Finance (net income): Financials -> Income statement -> Net Income Common Stockholders
        // ! Yahoo Finance (shareholders' equity): Financials -> Balance sheet -> Total Equity Gross Minority Interest -> Stockholders' Equity
        // ! Yahoo Finance: Statistics -> Return on Equity (ttm)

        double returnOnEquity = ((double) netIncome / shareholdersEquity) * 100;

        return returnOnEquity;
    }

    public static double calculateEarningsPerShareGrowth(double currentPeriodEPS, double previousPeriodEPS) {
        // * Earnings per share (EPS) Growth
        // Measures how much a company's earnings per share increase over time, showing how efficiently it converts
        // profits into shareholder value.
        // ! Yahoo Finance: Financials → Income Statement → EPS (Diluted)

        double earningsPerShareGrowth = ((double) (currentPeriodEPS - previousPeriodEPS) / previousPeriodEPS) * 100;

        return earningsPerShareGrowth;
    }

    public static double calculateRevenueGrowth(long currentPeriodRevenue, long previousPeriodRevenue) {
        // * Revenue growth
        // Measures how much a company's total sales increase over a specific period (year-to-year/quarter-over-quarter)
        // Reflects whether the company is expanding its business and demand for its products or services is rising.
        // ! Yahoo Finance: Financials → Income Statement → Total Revenue

        double revenueGrowthPercentage = ((double) (currentPeriodRevenue - previousPeriodRevenue) / previousPeriodRevenue) * 100;

        return revenueGrowthPercentage;
    }
}