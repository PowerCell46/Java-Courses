
// How well a company’s management uses its resources to generate profits and returns.
public class ManagementEfficiency {

    public static double returnOnAssets(long totalAssets, long netIncome) {
        // * Return on assets (ROA)
        // how efficiently a company uses its total assets to generate profit

        // ! Yahoo finance: Statistics: Return on Assets (ttm)

        return (double) totalAssets / netIncome;
    }

    // Inventory Turnover Ratio

    // Working Capital Turnover

    public static double returnOnEquity(long netIncome, long shareholdersEquity) {
        // * Return on equity (ROE)
        // Equity: Собствен капитал
        // Measures how effectively a company generates profit from its shareholders' invested capital.
        // It reflects management: higher -> company is using its equity to produce strong returns for investors.

        // ! Yahoo Finance: Statistics: Return on Equity (ttm)

        return ((double) netIncome / shareholdersEquity) * 100;
    }

    public static double assetTurnoverRatio(double totalRevenue, double totalAssets) {
        // * Asset turnover ratio
        // How efficiently a company uses its assets to generate revenue
        // Higher ratio → company uses assets efficiently to produce revenue.
        // Lower ratio → assets may be underutilized or the business is capital-intensive.

        // ! Yahoo finance: financials: income statement: total revenue
        // ! Yahoo finance: balance sheet: total assets

        // double totalAssets = beginningAssets + (double) endingAssets / 2;
        return totalRevenue / totalAssets;
    }
}
