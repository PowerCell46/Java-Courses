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

//        double sharePrice = 56.83;
//        long stockholdersEquity = 143_486_000;
//        long totalSharesOutstanding = 3_370_000_000L;
//        System.out.println(calculatePriceToBooks(sharePrice, stockholdersEquity, totalSharesOutstanding));

//        long freeCashFlow = 61_604_000;
//        long marketCap = 253_903_000_000L;
//        System.out.println(calculatePriceToFreeCashFlow(freeCashFlow, marketCap));

        // * Return on invested capital
        // Measures how efficiently a company generates profit from all the capital it controls.
        // Shows how well a company turns invested money into returns, regardless of financing structure.

        // TODO: skipped ---

//        long currentAssets = 160_897_000;
//        long currentLiabilities = 217_528_000;
//
//        System.out.println(calculateCurrentRatio(currentAssets, currentLiabilities));

        // * Quick ratio (Acid-Test ratio)
        // Measures a company's ability to meet short-term liabilities using its most liquid assets only.
        // It excludes inventory and prepaid expenses, which may take longer to convert into cash.

        long currentAssets = 160_897_000;
        long currentLiabilities = 217_528_000;
        // TODO: missing parameters ---

//        long EBIT = 148_924_000;
//        long interestExpense = 8_145_000;
//
//        System.out.println(calculateInterestCoverageRatio(EBIT, interestExpense));

//        long totalRevenue = 311_938_000;
//        long costOfRevenue = 50_068_000;
//
//        System.out.println(calculateGrossMargin(totalRevenue, costOfRevenue));

//        double dividendsPerShare = 1.099;
//        double earningsPerShare = 3.88;
//
//        System.out.println(calculateDividendPayoutRatio(dividendsPerShare, earningsPerShare));

//        double earningsPerShare = 3.88;
//        double sharePrice = 49.46;
//
//        System.out.println(calculateEarningsYield(earningsPerShare, sharePrice));
    }

    public static long calculateOperatingCashFlow(long netIncome, long nonCashExpenses, long changesInWorkingCapital) {
        // * Operating cash flow
        // measures the actual cash generated from core business operations — before financing or investing activities.
        // Strong OCF means the company can fund operations, pay dividends, and invest in growth without taking on new debt.

        // ! Yahoo finance: financials: cash flow: operating cash flow

        return netIncome + nonCashExpenses + changesInWorkingCapital;
    }

    public static double calculatePriceToEarningsToGrowthRatio(double priceToEarningsRatio, int earningsGrowthRate) {
        // * Price / Earnings to Growth (PEG) ratio
        // measures how expensive a stock is relative to its expected earnings growth.
        // < 1.0: Undervalued (growth not fully priced in)
        // > 1.0: Overvalued (price too high for its growth)

        // ! Yahoo finance: statistics: PEG Ratio (5yr expected)

        return priceToEarningsRatio / earningsGrowthRate;
    }

    public static double calculateEarningsYield(double earningsPerShare, double sharePrice) {
        // * Earnings yield
        // Shows how much earnings a company generates per 1 dollar of its stock price
        // Higher earnings yield: the stock might be undervalued
        // Lower earnings yield: the stock might be expensive or investors expect high future growth

        return (earningsPerShare / sharePrice) * 100;
    }

    public static double calculateBookValuePerShare(long totalAssets, long totalLiabilities, int preferredEquity, long totalOutstandingCommonShares) {
        // * Book value per share
        // Represents the equity value of a company per outstanding share according to its balance sheet
        // BVPS < current stock price → investors expect future growth (market values company above its book value)
        // BVPS > current stock price → stock might be undervalued or market expects poor performance
        // A rising BVPS over time = company is increasing shareholder value

        // ! Yahoo finance: statistics: Book Value Per Share

        long totalShareholdersEquity = totalAssets - totalLiabilities;
        return (double) (totalShareholdersEquity - preferredEquity) / totalOutstandingCommonShares;
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

    public static double calculateGrossMargin(long totalRevenue, long costOfRevenue) {
        // * Gross margin
        // Measures how much profit a company keeps from its revenue after accounting for the direct costs of producing
        // goods or services. It reflects pricing power, production efficiency, and competitive advantage.

        // ! Yahoo finance: financials: Income statement: Total revenue
        // ! Yahoo finance: financials: Income statement: Cost of revenue

        double grossMargin = ((double) (totalRevenue - costOfRevenue) / totalRevenue) * 100;

        return grossMargin;
    }

    public static double calculateInterestCoverageRatio(long EBIT, long interestExpense) {
        // * Interest coverage ratio
        // Measures how easily a company can pay interest on its outstanding debt using its operating profits.
        // It's a key indicator of financial stability and solvency, showing how safe the firm's debt load its relative
        // to its earnings power.

        // ! Yahoo finance: financials: Income Statement: EBIT
        // ! Yahoo finance: financials: Income Statement: Interest Expense

        double interestCoverageRatio = (double) EBIT / interestExpense;

        return interestCoverageRatio;
    }

    public static double calculateCurrentRatio(long currentAssets, long currentLiabilities) {
        // * Current ratio
        // Measures a company's ability to meet its short-term obligations using its short-term assets.
        // It reflects liquidity - the firm's capacity to cover debts within a year without raising new capital.

        // <1.0 → the company may struggle to cover short-term debts.
        // 1.5–2.5 → considered healthy and efficient for most industries.
        // >3.0 → possibly too much idle capital or inventory buildup.

        // ! Yahoo finance: statistics: Current Ratio (mrq)

        double currentRatio = (double) currentAssets / currentLiabilities;

        return currentRatio;
    }

    public static double calculatePriceToFreeCashFlow(long freeCashFlow, long marketCap) {
        // * Price to free cash flow (P/FCF)
        // Measures how much investors are paying for each dollar of free cash flow the company generates.
        // Lower P/FCF -> potentially undervalued; higher -> investors expect growth.

        // ! Yahoo finance: market cap: Summary
        // ! Yahoo finance: free cash flow: Financials: Cash flow: Free cash flow

        double priceToFreeCashFlow = (double) marketCap / freeCashFlow;

        return priceToFreeCashFlow;
    }

    public static double calculatePriceToBooks(double sharePrice, long stockholdersEquity, long totalSharesOutstanding) {
        // * Price to book (P/B)
        // Compares a company's market value to its book value(net assets). Shows how much investors are paying relative
        // to the company's actual net worth. Low P/B: undervaluation; High P/B: growth expectations/overvaluation

        // ! Yahoo finance: Statistics: Price/Book

        double bookValuePerShare = (double) stockholdersEquity / totalSharesOutstanding;
        double priceToBooks = sharePrice / bookValuePerShare;

        return priceToBooks;
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
        // * Free cash flow (FCF)
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