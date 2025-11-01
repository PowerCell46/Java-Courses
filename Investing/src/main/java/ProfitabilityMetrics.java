public final class ProfitabilityMetrics {

    public static double returnOnInvestedCapital(long netOperatingProfitAfterTax, long investedCapital) {
        // * Return on invested capital (ROIC)
        // measures the return a company earns on all capital invested
        // For every unit of capital invested, how much profit does management generate after taxes?

        return (double) netOperatingProfitAfterTax / investedCapital;
    }

    public static double calculateGrossMargin(long totalRevenue, long costOfRevenue) {
        // * Gross margin
        // Measures how much profit a company keeps from its revenue after accounting for the direct costs of producing
        // goods or services. It reflects pricing power, production efficiency, and competitive advantage.

        // ! Yahoo finance: financials: Income statement: Total revenue
        // ! Yahoo finance: financials: Income statement: Cost of revenue

        return ((double) (totalRevenue - costOfRevenue) / totalRevenue) * 100;
    }

    public static double calculateNetMargin(long netIncome, long revenue) {
        // * Profit margin (Net and operating)
        // Measures how much of a company's revenue turns into profit - showing efficiency, pricing power, and cost control.
        // - Net margin: what remains after all expenses, interest, and taxes

        // ! Yahoo finance: Statistics: Profit margin

        return ((double) netIncome / revenue) * 100;
    }

    public static double calculateOperatingMargin(long operatingIncome, long revenue) {
        // * Profit margin (Net and operating)
        // Measures how much of a company's revenue turns into profit - showing efficiency, pricing power, and cost control.
        // - Operating margin: how much profit the core business generates before interest and taxes.

        // ! Yahoo finance: Statistics: Operating margin

        return ((double) operatingIncome / revenue) * 100;
    }

    public static double calculateEarningsYield(double earningsPerShare, double sharePrice) {
        // * Earnings yield
        // Shows how much earnings a company generates per 1 dollar of its stock price
        // Higher earnings yield: the stock might be undervalued
        // Lower earnings yield: the stock might be expensive or investors expect high future growth

        return (earningsPerShare / sharePrice) * 100;
    }
}
