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

//        long operatingCashFlow = 121_527_000;
//        long capitalExpenditure = 59_923_000;
//        System.out.println(calculateFreeCashFlow(operatingCashFlow, capitalExpenditure));

//        long totalDebt = 102_787_000;
//        long stockholdersEquity = 143_486_000;
//        System.out.println(calculateTotalDebtEquity(totalDebt, stockholdersEquity));

//        long operatingIncome = 142_799_000;
//        long revenue = 311_938_000;
//        long netIncome = 111_068_000;
//
//        System.out.println(calculateOperatingMargin(operatingIncome, revenue));
//        System.out.println(calculateNetMargin(netIncome, revenue));

//        double sharePrice = 56.83;
//        double earningsPerShare = 3.86;
//
//        System.out.println(calculatePriceToEarnings(sharePrice, earningsPerShare));
    }

    public static double calculatePriceToEarnings(double sharePrice, double earningsPerShare) {
        // * Price to earnings (P/E)
        // How much investors are willing to pay for each dollar of a company's earnings. Higher P/E: market expects stronger
        // future growth; Lower P/E: undervaluation or weak prospects

        // ! Yahoo finance: Statistics: Trailing P/E (ttm): uses last 12 months’ earnings (TTM = trailing twelve months).
        // ! Yahoo finance: Statistics: Forward P/E: uses projected earnings for the next fiscal year.

        double priceToEarnings = sharePrice / earningsPerShare;

        return priceToEarnings; // * Investors pay "priceToEarnings" for every $1 of earnings.
    }

    public static double calculateNetMargin(long netIncome, long revenue) {
        // * Profit margin (Net and operating)
        // Measures how much of a company's revenue turns into profit - showing efficiency, pricing power, and cost control.
        // - Net margin: what remains after all expenses, interest, and taxes

        // ! Yahoo finance: Statistics: Profit margin

        double netProfitMargin = ((double) netIncome / revenue) * 100;

        return netProfitMargin;
    }

    public static double calculateOperatingMargin(long operatingIncome, long revenue) {
        // * Profit margin (Net and operating)
        // Measures how much of a company's revenue turns into profit - showing efficiency, pricing power, and cost control.
        // - Operating margin: how much profit the core business generates before interest and taxes.

        // ! Yahoo finance: Statistics: Operating margin

        double operatingMargin = ((double) operatingIncome / revenue) * 100;

        return operatingMargin;
    }

    public static double calculateTotalDebtEquity(long totalDebt, long stockholdersEquity) {
        // * Debt to equity ratio (D/E)
        // Shows how much a company relies on borrowed money/debt vs shareholder's capital (equity) to finance its operations.
        // Higher ratios: greater dependence on debt and potentially higher risk

        // ! Yahoo Finance: Statistics: Total Debt/Equity (mrq)

        double totalDebtEquity = (double) totalDebt / stockholdersEquity;

        return totalDebtEquity;
    }

    public static long calculateFreeCashFlow(long operatingCashFlow, long capitalExpenditure) {
        // * Free cash flow
        // Measures the actual cash a company generates after covering all its operating expenses and capital expenditures.
        // It's one of the most important indicators of a company's financial health, flexibility, and ability
        // to return value to shareholders (dividends, buybacks, reinvestment).

        // ! Yahoo Finance: Financials -> Cash flow -> Free cash flow

        long freeCashFlow = operatingCashFlow - capitalExpenditure; // capital expenditure is regularly a negative num

        return freeCashFlow;
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