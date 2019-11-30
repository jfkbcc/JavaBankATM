package edu.cuny.csi.csc330.bankatm;

import java.text.NumberFormat;

public class BankAccount
{
    private int id;
    private int balance;
    private String name;

    public BankAccount(int id, String name, int balance)
    {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public String getFormattedBalance() {
        return NumberFormat.getCurrencyInstance().format(balance / 100.0);
    }

    public String getName() {
        return name;
    }
}
