public final class CashFlowMetrics {

    public static long calculateOperatingCashFlow(long netIncome, long nonCashExpenses, long changesInWorkingCapital) {
        // * Operating cash flow
        // measures the actual cash generated from core business operations — before financing or investing activities.
        // Strong OCF means the company can fund operations, pay dividends, and invest in growth without taking on new debt.

        // ! Yahoo finance: financials: cash flow: operating cash flow

        return netIncome + nonCashExpenses + changesInWorkingCapital;
    }

    public static long calculateCapitalExpenditures() {
        // * Capital expenditures (CapEx)
        // assess how much a company invests in maintaining and expanding its asset base (factories, equipment, R&D facilities, etc.)
        // CapEx represents cash spent on long-term assets — factories, machinery, vehicles, buildings, or technology infrastructure.

        // ! Yahoo finance: Financials: cash flow: Capital Expenditure

        return 0;
    }

    public static long calculateFreeCashFlow(long operatingCashFlow, long capitalExpenditure) {
        // * Free cash flow (FCF)
        // Measures the actual cash a company generates after covering all its operating expenses and capital expenditures.
        // It's one of the most important indicators of a company's financial health, flexibility, and ability
        // to return value to shareholders (dividends, buybacks, reinvestment).

        // ! Yahoo Finance: Financials -> Cash flow -> Free cash flow

        return operatingCashFlow - capitalExpenditure;
    }

    public static double calculatePriceToFreeCashFlow(long freeCashFlow, long marketCap) {
        // * Price to free cash flow (P/FCF)
        // Measures how much investors are paying for each dollar of free cash flow the company generates.
        // Lower P/FCF -> potentially undervalued; higher -> investors expect growth.

        // ! Yahoo finance: market cap: Summary
        // ! Yahoo finance: free cash flow: Financials: Cash flow: Free cash flow

        return (double) marketCap / freeCashFlow;
    }

    public static double calculateShareBuybackRatio(long valueOfSharesRepurchased, long netIncome) {
        // * Share buyback ratio
        // how much of its market value or earnings a company uses to repurchase its own shares
        // shows how aggressively a company is returning capital to shareholders by reducing share count

        // ! Yahoo finance: financials: cash flow: Repurchase of Capital Stock

        return (double) valueOfSharesRepurchased / netIncome;
    }

    public static double calculateDividendYield(double annualDividendsPerShare, double currentSharePrice) {
        // * Dividend Yield
        // The Dividend Yield measures the annual dividend income an investor earns relative to the stock’s current market price.
        // It’s expressed as a percentage and shows how much return you get from dividends alone

        // ! Yahoo finance: summary: Forward Dividend & Yield

        return (annualDividendsPerShare / currentSharePrice) * 100;
    }

    public static double calculateDividendPayoutRatio(double dividendsPerShare, double earningsPerShare) {
        // * Dividend payout ratio
        // Shows what percentage of a company's earnings are distributed to shareholders as dividends. Indicates how sustainable
        // the dividend it - lower values: the company retains more profits for growth; higher values: it's returning more to shareholders.

        // ! Yahoo finance: statistics: Payout ratio

        return (dividendsPerShare / earningsPerShare) * 100;
    }
}

