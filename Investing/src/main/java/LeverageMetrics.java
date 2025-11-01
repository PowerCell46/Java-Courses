public final class LeverageMetrics {

    public static double calculateTotalDebtEquity(long totalDebt, long stockholdersEquity) {
        // * Debt to equity ratio (D/E)
        // Shows how much a company relies on borrowed money/debt vs shareholder's capital (equity) to finance its operations.
        // Higher ratios: greater dependence on debt and potentially higher risk

        // ! Yahoo Finance: Statistics: Total Debt/Equity (mrq)

        return (double) totalDebt / stockholdersEquity;
    }

    public static double calculateInterestCoverageRatio(long EBIT, long interestExpense) {
        // * Interest coverage ratio
        // Measures how easily a company can pay interest on its outstanding debt using its operating profits.
        // It's a key indicator of financial stability and solvency, showing how safe the firm's debt load its relative
        // to its earnings power.

        // ! Yahoo finance: financials: Income Statement: EBIT
        // ! Yahoo finance: financials: Income Statement: Interest Expense

        return (double) EBIT / interestExpense;
    }

    public static long calculateTotalLiabilities(long currentLiabilities, long nonCurrentLiabilities) {
        // * Total liabilities
        // Represents all debts and financial obligations a company owes to external parties

        // ! Yahoo finance: financials: balance sheet: Total Liabilities Net Minority Interest

        return currentLiabilities + nonCurrentLiabilities;
    }
}
