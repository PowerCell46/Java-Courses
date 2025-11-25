package com.investing.metrics.ownership;

import com.investing.util.ValidationUtils;

/**
 * Utility class for calculating ownership-related financial metrics.
 * These metrics measure how a company's shares are distributed among different types of investors.
 */
public final class OwnershipMetrics {

    private OwnershipMetrics() {
        throw new AssertionError("Utility class");
    }

    /**
     * Calculates Institutional Ownership percentage.
     * Shows how much of a company's shares are held by large professional investors, which can indicate confidence, liquidity.
     * High institutional ownership: professional investors trust the company, adds liquidity, may stabilize stock price.
     * Very low institutional ownership: less market attention, possibly more volatile or illiquid.
     *
     * @param sharedHeldByInstitutions shares held by institutions
     * @param totalSharesOutstanding total shares outstanding
     * @return institutional ownership as a percentage (e.g., 75.5 for 75.5%)
     * @throws com.investing.exception.DivisionByZeroException if totalSharesOutstanding is zero
     */
    public static double calculateInstitutionalOwnership(long sharedHeldByInstitutions, long totalSharesOutstanding) {
        ValidationUtils.validateNonZero(totalSharesOutstanding, "Institutional Ownership");
        return ((double) sharedHeldByInstitutions / totalSharesOutstanding) * 100;
    }

    /**
     * Calculates Insider Ownership percentage.
     * Shows how much of a company is owned by executives, directors, and key insiders.
     * Higher insider ownership: management's interests are aligned with shareholders, less likely to act against minority investors.
     * Lower insider ownership: potential agency problems, less skin in the game.
     *
     * @param sharesHeldByInsiders shares held by insiders
     * @param totalSharesOutstanding total shares outstanding
     * @return insider ownership as a percentage (e.g., 12.5 for 12.5%)
     * @throws com.investing.exception.DivisionByZeroException if totalSharesOutstanding is zero
     */
    public static double calculateInsiderOwnership(long sharesHeldByInsiders, long totalSharesOutstanding) {
        ValidationUtils.validateNonZero(totalSharesOutstanding, "Insider Ownership");
        return ((double) sharesHeldByInsiders / totalSharesOutstanding) * 100;
    }
}


