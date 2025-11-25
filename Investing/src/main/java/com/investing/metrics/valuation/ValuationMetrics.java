package com.investing.metrics.valuation;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating valuation-related financial metrics.
 * These metrics help assess whether a company's stock is undervalued, fairly valued, or overvalued.
 */
public final class ValuationMetrics {

    private ValuationMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Enterprise Value to Revenue ratio (EV/Sales).
     * Particularly useful for growth companies or early-stage firms where earnings aren't yet stable or positive.
     * Shows how much investors are paying for each unit of revenue — including both debt and equity.
     *
     * @param enterpriseValue enterprise value
     * @param totalRevenue total revenue
     * @return EV/Revenue ratio
     * @throws com.investing.exception.DivisionByZeroException if totalRevenue is zero
     */
    public static double calculateEnterpriseValueToRevenue(double enterpriseValue, double totalRevenue) {
        ValidationUtils.validateNonZero(totalRevenue, "Enterprise Value to Revenue");
        return enterpriseValue / totalRevenue;
    }

    /**
     * Calculates Enterprise Value to EBITDA ratio (EV/EBITDA).
     * Measures a company's total enterprise value relative to its operating profitability.
     * Shows how much investors are paying for each unit of operating earnings before non-cash and financing items.
     *
     * @param enterpriseValue enterprise value
     * @param EBITDA earnings before interest, taxes, depreciation, and amortization
     * @return EV/EBITDA ratio
     * @throws com.investing.exception.DivisionByZeroException if EBITDA is zero
     */
    public static double calculateEnterpriseValueToEBITDA(double enterpriseValue, double EBITDA) {
        ValidationUtils.validateNonZero(EBITDA, "Enterprise Value to EBITDA");
        return enterpriseValue / EBITDA;
    }

    /**
     * Calculates Enterprise Value (EV).
     * It captures the total value of a company's operations, including debt and cash.
     * Represents the total theoretical takeover price of a company.
     *
     * @param marketCapitalization market capitalization
     * @param shortTermDebt short-term debt
     * @param longTermDebt long-term debt
     * @param preferredStock preferred stock value
     * @param minorityInterest minority interest
     * @param cashOnHand cash on hand
     * @param shortTermInvestments short-term investments
     * @return enterprise value
     */
    public static long calculateEnterpriseValue(
            long marketCapitalization,
            long shortTermDebt,
            long longTermDebt,
            long preferredStock,
            long minorityInterest,
            long cashOnHand,
            long shortTermInvestments
    ) {
        long totalDebt = shortTermDebt + longTermDebt;
        long cashAndCashEquivalents = cashOnHand + shortTermInvestments;

        return marketCapitalization + totalDebt + preferredStock + minorityInterest - cashAndCashEquivalents;
    }

    /**
     * Calculates Market Capitalization.
     * The total market value of all a company's outstanding shares.
     *
     * @param sharePrice current share price
     * @param totalSharesOutstanding total shares outstanding
     * @return market capitalization
     */
    public static double calculateMarketCapitalization(double sharePrice, long totalSharesOutstanding) {
        return sharePrice * totalSharesOutstanding;
    }

    /**
     * Calculates Tangible Book Value.
     * Represents the net worth of a company excluding intangible assets (goodwill, patents, trademarks).
     * "If the company were liquidated today, how much real, physical asset value would remain for shareholders?"
     *
     * @param totalAssets total assets
     * @param intangibleAssets intangible assets
     * @param totalLiabilities total liabilities
     * @return tangible book value
     */
    public static double calculateTangibleBookValue(double totalAssets, double intangibleAssets, double totalLiabilities) {
        return totalAssets - intangibleAssets - totalLiabilities;
    }

    /**
     * Calculates Book Value Per Share.
     * Represents the equity value of a company per outstanding share according to its balance sheet.
     * BVPS < current stock price → investors expect future growth (market values company above its book value).
     * BVPS > current stock price → stock might be undervalued or market expects poor performance.
     * A rising BVPS over time = company is increasing shareholder value.
     *
     * @param totalAssets total assets
     * @param totalLiabilities total liabilities
     * @param preferredEquity preferred equity
     * @param totalOutstandingCommonShares total outstanding common shares
     * @return book value per share
     * @throws com.investing.exception.DivisionByZeroException if totalOutstandingCommonShares is zero
     */
    public static double calculateBookValuePerShare(long totalAssets, long totalLiabilities, int preferredEquity, long totalOutstandingCommonShares) {
        ValidationUtils.validateNonZero(totalOutstandingCommonShares, "Book Value Per Share");
        long totalShareholdersEquity = totalAssets - totalLiabilities;
        return (double) (totalShareholdersEquity - preferredEquity) / totalOutstandingCommonShares;
    }

    /**
     * Calculates Price to Book (P/B) ratio.
     * Compares a company's market value to its book value (net assets). Shows how much investors are paying relative
     * to the company's actual net worth. Low P/B: undervaluation; High P/B: growth expectations/overvaluation.
     *
     * @param sharePrice current share price
     * @param stockholdersEquity stockholders' equity
     * @param totalSharesOutstanding total shares outstanding
     * @return P/B ratio
     * @throws com.investing.exception.DivisionByZeroException if totalSharesOutstanding or stockholdersEquity is zero
     */
    public static double calculatePriceToBooks(double sharePrice, long stockholdersEquity, long totalSharesOutstanding) {
        ValidationUtils.validateNonZero(totalSharesOutstanding, "Price to Book");
        ValidationUtils.validateNonZero(stockholdersEquity, "Price to Book");
        double bookValuePerShare = (double) stockholdersEquity / totalSharesOutstanding;
        ValidationUtils.validateNonZero(bookValuePerShare, "Price to Book");
        return sharePrice / bookValuePerShare;
    }

    /**
     * Calculates Price to Earnings (P/E) ratio.
     * How much investors are willing to pay for each dollar of a company's earnings.
     * Higher P/E: market expects stronger future growth.
     * Lower P/E: undervaluation or weak prospects.
     *
     * @param sharePrice current share price
     * @param earningsPerShare earnings per share
     * @return P/E ratio (investors pay this amount for every $1 of earnings)
     * @throws com.investing.exception.DivisionByZeroException if earningsPerShare is zero
     */
    public static double calculatePriceToEarnings(double sharePrice, double earningsPerShare) {
        ValidationUtils.validateNonZero(earningsPerShare, "Price to Earnings");
        return sharePrice / earningsPerShare;
    }

    /**
     * Calculates Price / Earnings to Growth (PEG) ratio.
     * Measures how expensive a stock is relative to its expected earnings growth.
     * < 1.0: Undervalued (growth not fully priced in)
     * > 1.0: Overvalued (price too high for its growth)
     *
     * @param priceToEarningsRatio price to earnings ratio
     * @param earningsGrowthRate earnings growth rate (as decimal, e.g., 0.15 for 15%)
     * @return PEG ratio
     * @throws com.investing.exception.DivisionByZeroException if earningsGrowthRate is zero
     */
    public static double calculatePriceToEarningsToGrowthRatio(double priceToEarningsRatio, double earningsGrowthRate) {
        ValidationUtils.validateNonZero(earningsGrowthRate, "PEG Ratio");
        return priceToEarningsRatio / earningsGrowthRate;
    }

    /**
     * Calculates Revenue Per Share.
     * Shows how much revenue each share represents: purely sales per share.
     *
     * @param totalRevenue total revenue
     * @param sharesOutstanding shares outstanding
     * @return revenue per share
     * @throws com.investing.exception.DivisionByZeroException if sharesOutstanding is zero
     */
    public static double calculateRevenuePerShare(double totalRevenue, double sharesOutstanding) {
        ValidationUtils.validateNonZero(sharesOutstanding, "Revenue Per Share");
        return totalRevenue / sharesOutstanding;
    }
}


