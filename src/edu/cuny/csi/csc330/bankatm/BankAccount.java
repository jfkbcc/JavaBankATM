package edu.cuny.csi.csc330.bankatm;

import java.text.NumberFormat;

public class BankAccount
{
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

    public int getBalance(BankAccountType accountType) {
        return (accountType == BankAccountType.Checking ? this.checkingBalance : this.savingsBalance);
    }

    public String getFormattedBalance(BankAccountType accountType) {
        return formatCurrency(getBalance(accountType));
    }

    public static String formatCurrency(int val) {
        return NumberFormat.getCurrencyInstance().format(val / 100.0);
    }
}
