public final class ValuationMetrics {

    public static double calculateEnterpriseValueToRevenue(double enterpriseValue, double totalRevenue) {
        // * Enterprise value (EV) / Sales
        // particularly useful for growth companies or early-stage firms where earnings aren't yet stable or positive
        // how much investors are paying for each unit of revenue — including both debt and equity

        // ! Yahoo finance: statistics: Enterprise Value/Revenue

        return enterpriseValue / totalRevenue;
    }

    public static double calculateEnterpriseValueToEBITDA(double enterpriseValue, double EBITDA) {
        // * Enterprise value (EV) / Earnings Before Interest, Taxes, Depreciation, and Amortization (EBITDA)
        // Measures a company’s total enterprise value relative to its operating profitability
        // Shows how much investors are paying for each unit of operating earnings before non-cash and financing items

        // ! Yahoo finance: statistics: Enterprise Value/EBITDA

        return enterpriseValue / EBITDA;
    }

    public static long calculateEnterpriseValue(
            long marketCapitalization,
            long shortTermDebt,
            long longTermDebt,
            long preferredStock,
            long minorityInterest,
            long cashOnHand,
            long shortTermInvestments
    ) {
        // * Enterprise value (EV)
        // it captures the total value of a company’s operations, including debt and cash
        // represents the total theoretical takeover price of a company

        // ! Yahoo finance: statistics: Enterprise value

        long totalDept = shortTermDebt + longTermDebt;
        long cashAndCashEquivalents = cashOnHand + shortTermInvestments;

        return marketCapitalization + totalDept + preferredStock + minorityInterest - cashAndCashEquivalents;
    }

    public static double calculateMarketCapitalization(double sharePrice, long totalSharesOutstanding) {
        // * Market capitalization
        // The total market value of all a company's outstanding shares

        // ! Yahoo finance: summary: Market cap

        return sharePrice * totalSharesOutstanding;
    }

    public static double calculateTangibleBookValue(double totalAssets, double intangibleAssets, double totalLiabilities) {
        // * Tangible book value
        // represents the net worth of a company excluding intangible assets (goodwill, patents, trademarks)
        // “If the company were liquidated today, how much real, physical asset value would remain for shareholders?”

        return totalAssets - intangibleAssets - totalLiabilities;
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

    public static double calculatePriceToBooks(double sharePrice, long stockholdersEquity, long totalSharesOutstanding) {
        // * Price to book (P/B)
        // Compares a company's market value to its book value(net assets). Shows how much investors are paying relative
        // to the company's actual net worth. Low P/B: undervaluation; High P/B: growth expectations/overvaluation

        // ! Yahoo finance: Statistics: Price/Book

        double bookValuePerShare = (double) stockholdersEquity / totalSharesOutstanding;

        return sharePrice / bookValuePerShare;
    }

    public static double calculatePriceToEarnings(double sharePrice, double earningsPerShare) {
        // * Price to earnings (P/E)
        // How much investors are willing to pay for each dollar of a company's earnings. Higher P/E: market expects stronger
        // future growth; Lower P/E: undervaluation or weak prospects

        // ! Yahoo finance: Statistics: Trailing P/E (ttm): uses last 12 months’ earnings (TTM = trailing twelve months).
        // ! Yahoo finance: Statistics: Forward P/E: uses projected earnings for the next fiscal year.

        return sharePrice / earningsPerShare; // * Investors pay "priceToEarnings" for every $1 of earnings.
    }

    public static double calculatePriceToEarningsToGrowthRatio(double priceToEarningsRatio, double earningsGrowthRate) {
        // * Price / Earnings to Growth (PEG) ratio
        // measures how expensive a stock is relative to its expected earnings growth.
        // < 1.0: Undervalued (growth not fully priced in)
        // > 1.0: Overvalued (price too high for its growth)

        // ! Yahoo finance: statistics: PEG Ratio (5yr expected)

        return priceToEarningsRatio / earningsGrowthRate;
    }

    public static double calculateRevenuePerShare(double totalRevenue, double sharesOutstanding) {
        // * Revenue per share
        // how much revenue each share represents: purely sales per share

        return totalRevenue / sharesOutstanding;
    }
}
