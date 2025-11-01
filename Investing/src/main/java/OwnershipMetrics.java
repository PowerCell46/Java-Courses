public final class OwnershipMetrics {

    public static double calculateInstitutionalOwnership(long sharedHeldByInstitutions, long totalSharesOutstanding) {
        // * Institutional Ownership
        // How much of a company's shares are held by large professional investors, which can indicate confidence, liquidity
        // High institutional ownership: professional investors trust the company, adds liquidity, may stabilize stock price.
        // Very low institutional ownership: less market attention, possibly more volatile or illiquid.

        // ! Yahoo finance: statistics: % Held by Institutions

        return ((double) sharedHeldByInstitutions / totalSharesOutstanding) * 100;
    }

    public static double calculateInsiderOwnership(long sharesHeldByInsiders, long totalSharesOutstanding) {
        // * Insider ownership
        // How much of a company is owned by executives, directors, and key insiders
        // Higher insider ownership: management’s interests are aligned with shareholders, less likely to act against minority investors.
        // Lower insider ownership: potential agency problems, less skin in the game.

        // ! Yahoo finance: statistics: % Held by Insiders

        return ((double) sharesHeldByInsiders / totalSharesOutstanding) * 100;
    }
}
