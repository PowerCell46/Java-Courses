package com.investing.model;

/**
 * Represents financial data for a company used in metric calculations.
 * This class encapsulates all the financial inputs needed for various investment metrics.
 */
public class FinancialData {
    private long revenue;
    private long totalRevenue;
    private long netIncome;
    private long operatingIncome;
    private long netOperatingProfitAfterTax;
    private long costOfRevenue;
    private long costOfGoodsSold;
    private long totalAssets;
    private long intangibleAssets;
    private long currentAssets;
    private long inventory;
    private long totalLiabilities;
    private long currentLiabilities;
    private long nonCurrentLiabilities;
    private long stockholdersEquity;
    private long shareholdersEquity;
    private long totalDebt;
    private long shortTermDebt;
    private long longTermDebt;
    private long investedCapital;
    private long cashAndCashEquivalents;
    private long cashOnHand;
    private long shortTermInvestments;
    private long marketableSecurities;
    private long accountsReceivable;
    private long preferredStock;
    private long minorityInterest;
    private long EBIT;
    private long interestExpense;
    private long operatingCashFlow;
    private long capitalExpenditure;
    private long freeCashFlow;
    private long netIncomeForFCF;
    private long nonCashExpenses;
    private long changesInWorkingCapital;
    private long marketCapitalization;
    private long totalSharesOutstanding;
    private double sharePrice;
    private double earningsPerShare;
    private double annualDividendsPerShare;
    private double dividendsPerShare;
    private long valueOfSharesRepurchased;
    private long revenueInForeignCurrencies;
    private long sharedHeldByInstitutions;
    private long sharesHeldByInsiders;
    private double rAndD;
    private long previousPeriodRevenue;
    private double previousPeriodEPS;
    private long enterpriseValue;
    private double EBITDA;
    private int preferredEquity;
    
    // Getters and setters
    public long getRevenue() { return revenue; }
    public void setRevenue(long revenue) { this.revenue = revenue; }
    
    public long getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(long totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public long getNetIncome() { return netIncome; }
    public void setNetIncome(long netIncome) { this.netIncome = netIncome; }
    
    public long getOperatingIncome() { return operatingIncome; }
    public void setOperatingIncome(long operatingIncome) { this.operatingIncome = operatingIncome; }
    
    public long getNetOperatingProfitAfterTax() { return netOperatingProfitAfterTax; }
    public void setNetOperatingProfitAfterTax(long netOperatingProfitAfterTax) { this.netOperatingProfitAfterTax = netOperatingProfitAfterTax; }
    
    public long getCostOfRevenue() { return costOfRevenue; }
    public void setCostOfRevenue(long costOfRevenue) { this.costOfRevenue = costOfRevenue; }
    
    public long getCostOfGoodsSold() { return costOfGoodsSold; }
    public void setCostOfGoodsSold(long costOfGoodsSold) { this.costOfGoodsSold = costOfGoodsSold; }
    
    public long getTotalAssets() { return totalAssets; }
    public void setTotalAssets(long totalAssets) { this.totalAssets = totalAssets; }
    
    public long getIntangibleAssets() { return intangibleAssets; }
    public void setIntangibleAssets(long intangibleAssets) { this.intangibleAssets = intangibleAssets; }
    
    public long getCurrentAssets() { return currentAssets; }
    public void setCurrentAssets(long currentAssets) { this.currentAssets = currentAssets; }
    
    public long getInventory() { return inventory; }
    public void setInventory(long inventory) { this.inventory = inventory; }
    
    public long getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(long totalLiabilities) { this.totalLiabilities = totalLiabilities; }
    
    public long getCurrentLiabilities() { return currentLiabilities; }
    public void setCurrentLiabilities(long currentLiabilities) { this.currentLiabilities = currentLiabilities; }
    
    public long getNonCurrentLiabilities() { return nonCurrentLiabilities; }
    public void setNonCurrentLiabilities(long nonCurrentLiabilities) { this.nonCurrentLiabilities = nonCurrentLiabilities; }
    
    public long getStockholdersEquity() { return stockholdersEquity; }
    public void setStockholdersEquity(long stockholdersEquity) { this.stockholdersEquity = stockholdersEquity; }
    
    public long getShareholdersEquity() { return shareholdersEquity; }
    public void setShareholdersEquity(long shareholdersEquity) { this.shareholdersEquity = shareholdersEquity; }
    
    public long getTotalDebt() { return totalDebt; }
    public void setTotalDebt(long totalDebt) { this.totalDebt = totalDebt; }
    
    public long getShortTermDebt() { return shortTermDebt; }
    public void setShortTermDebt(long shortTermDebt) { this.shortTermDebt = shortTermDebt; }
    
    public long getLongTermDebt() { return longTermDebt; }
    public void setLongTermDebt(long longTermDebt) { this.longTermDebt = longTermDebt; }
    
    public long getInvestedCapital() { return investedCapital; }
    public void setInvestedCapital(long investedCapital) { this.investedCapital = investedCapital; }
    
    public long getCashAndCashEquivalents() { return cashAndCashEquivalents; }
    public void setCashAndCashEquivalents(long cashAndCashEquivalents) { this.cashAndCashEquivalents = cashAndCashEquivalents; }
    
    public long getCashOnHand() { return cashOnHand; }
    public void setCashOnHand(long cashOnHand) { this.cashOnHand = cashOnHand; }
    
    public long getShortTermInvestments() { return shortTermInvestments; }
    public void setShortTermInvestments(long shortTermInvestments) { this.shortTermInvestments = shortTermInvestments; }
    
    public long getMarketableSecurities() { return marketableSecurities; }
    public void setMarketableSecurities(long marketableSecurities) { this.marketableSecurities = marketableSecurities; }
    
    public long getAccountsReceivable() { return accountsReceivable; }
    public void setAccountsReceivable(long accountsReceivable) { this.accountsReceivable = accountsReceivable; }
    
    public long getPreferredStock() { return preferredStock; }
    public void setPreferredStock(long preferredStock) { this.preferredStock = preferredStock; }
    
    public long getMinorityInterest() { return minorityInterest; }
    public void setMinorityInterest(long minorityInterest) { this.minorityInterest = minorityInterest; }
    
    public long getEBIT() { return EBIT; }
    public void setEBIT(long EBIT) { this.EBIT = EBIT; }
    
    public long getInterestExpense() { return interestExpense; }
    public void setInterestExpense(long interestExpense) { this.interestExpense = interestExpense; }
    
    public long getOperatingCashFlow() { return operatingCashFlow; }
    public void setOperatingCashFlow(long operatingCashFlow) { this.operatingCashFlow = operatingCashFlow; }
    
    public long getCapitalExpenditure() { return capitalExpenditure; }
    public void setCapitalExpenditure(long capitalExpenditure) { this.capitalExpenditure = capitalExpenditure; }
    
    public long getFreeCashFlow() { return freeCashFlow; }
    public void setFreeCashFlow(long freeCashFlow) { this.freeCashFlow = freeCashFlow; }
    
    public long getNetIncomeForFCF() { return netIncomeForFCF; }
    public void setNetIncomeForFCF(long netIncomeForFCF) { this.netIncomeForFCF = netIncomeForFCF; }
    
    public long getNonCashExpenses() { return nonCashExpenses; }
    public void setNonCashExpenses(long nonCashExpenses) { this.nonCashExpenses = nonCashExpenses; }
    
    public long getChangesInWorkingCapital() { return changesInWorkingCapital; }
    public void setChangesInWorkingCapital(long changesInWorkingCapital) { this.changesInWorkingCapital = changesInWorkingCapital; }
    
    public long getMarketCapitalization() { return marketCapitalization; }
    public void setMarketCapitalization(long marketCapitalization) { this.marketCapitalization = marketCapitalization; }
    
    public long getTotalSharesOutstanding() { return totalSharesOutstanding; }
    public void setTotalSharesOutstanding(long totalSharesOutstanding) { this.totalSharesOutstanding = totalSharesOutstanding; }
    
    public double getSharePrice() { return sharePrice; }
    public void setSharePrice(double sharePrice) { this.sharePrice = sharePrice; }
    
    public double getEarningsPerShare() { return earningsPerShare; }
    public void setEarningsPerShare(double earningsPerShare) { this.earningsPerShare = earningsPerShare; }
    
    public double getAnnualDividendsPerShare() { return annualDividendsPerShare; }
    public void setAnnualDividendsPerShare(double annualDividendsPerShare) { this.annualDividendsPerShare = annualDividendsPerShare; }
    
    public double getDividendsPerShare() { return dividendsPerShare; }
    public void setDividendsPerShare(double dividendsPerShare) { this.dividendsPerShare = dividendsPerShare; }
    
    public long getValueOfSharesRepurchased() { return valueOfSharesRepurchased; }
    public void setValueOfSharesRepurchased(long valueOfSharesRepurchased) { this.valueOfSharesRepurchased = valueOfSharesRepurchased; }
    
    public long getRevenueInForeignCurrencies() { return revenueInForeignCurrencies; }
    public void setRevenueInForeignCurrencies(long revenueInForeignCurrencies) { this.revenueInForeignCurrencies = revenueInForeignCurrencies; }
    
    public long getSharedHeldByInstitutions() { return sharedHeldByInstitutions; }
    public void setSharedHeldByInstitutions(long sharedHeldByInstitutions) { this.sharedHeldByInstitutions = sharedHeldByInstitutions; }
    
    public long getSharesHeldByInsiders() { return sharesHeldByInsiders; }
    public void setSharesHeldByInsiders(long sharesHeldByInsiders) { this.sharesHeldByInsiders = sharesHeldByInsiders; }
    
    public double getRAndD() { return rAndD; }
    public void setRAndD(double rAndD) { this.rAndD = rAndD; }
    
    public long getPreviousPeriodRevenue() { return previousPeriodRevenue; }
    public void setPreviousPeriodRevenue(long previousPeriodRevenue) { this.previousPeriodRevenue = previousPeriodRevenue; }
    
    public double getPreviousPeriodEPS() { return previousPeriodEPS; }
    public void setPreviousPeriodEPS(double previousPeriodEPS) { this.previousPeriodEPS = previousPeriodEPS; }
    
    public long getEnterpriseValue() { return enterpriseValue; }
    public void setEnterpriseValue(long enterpriseValue) { this.enterpriseValue = enterpriseValue; }
    
    public double getEBITDA() { return EBITDA; }
    public void setEBITDA(double EBITDA) { this.EBITDA = EBITDA; }
    
    public int getPreferredEquity() { return preferredEquity; }
    public void setPreferredEquity(int preferredEquity) { this.preferredEquity = preferredEquity; }
}


