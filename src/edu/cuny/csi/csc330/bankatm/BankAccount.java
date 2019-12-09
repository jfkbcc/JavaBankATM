package edu.cuny.csi.csc330.bankatm;

import java.text.NumberFormat;

public class BankAccount
{
    public enum AccountType {
        None,
        Checking,
        Savings
    };

    public static AccountType getAccountType(String accountType) {
        switch (accountType.toLowerCase()) {
            case "checking":    return AccountType.Checking;
            case "savings":      return AccountType.Savings;
            default:            return AccountType.None;
        }
    }

    private int id;
    private int checkingBalance;
    private int savingsBalance;
    private String name;

    public BankAccount(int id, String name, int checking, int saving)
    {
        this.id = id;
        this.name = name;
        this.checkingBalance = checking;
        this.savingsBalance = saving;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance(AccountType accountType) {
        return (accountType == AccountType.Checking ? this.checkingBalance : this.savingsBalance);
    }

    public String getFormattedBalance(AccountType accountType) {
        return formatCurrency(getBalance(accountType));
    }

    public static String formatCurrency(int val) {
        return NumberFormat.getCurrencyInstance().format(val / 100.0);
    }
}
