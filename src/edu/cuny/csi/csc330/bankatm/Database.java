package edu.cuny.csi.csc330.bankatm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database
{
    private Connection dbConnection = null;
    private String dbName = "";

    public Database() {

    }

    public void close() {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dbConnection = null;
        }
    }

    public boolean open(String databaseName) {
        if (dbConnection == null) {
            try {
                // db parameters
                String url = "jdbc:sqlite:" + databaseName + ".db";
                dbConnection = DriverManager.getConnection(url);
                this.dbName = databaseName;

                System.out.println("Connection to SQLite has been established.");
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return false;
    }

    public boolean isClosed() {
        return (dbConnection == null);
    }

    public ArrayList<BankAccount> getAccounts() {
        String sql = "SELECT id, name, balance, balance_saving FROM accounts WHERE id > ?";

        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();

        try (PreparedStatement pstmt  = dbConnection.prepareStatement(sql))
        {
            // set the value
            pstmt.setInt(1, 0);

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int checking_balance = rs.getInt("balance");
                int saving_balance = rs.getInt("balance_saving");

                BankAccount bankAccount = new BankAccount(id, name, checking_balance, saving_balance);
                bankAccounts.add(bankAccount);

                System.out.println(id +  "\t" +
                        name + "\t" +
                        checking_balance + "\t" + saving_balance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bankAccounts;
    }

    public BankAccount authenticateAccount(String acctNumber, String pin) {
        String sql = "SELECT id, name, balance, balance_saving FROM accounts WHERE id = ? and pin = ?";

        try (PreparedStatement pstmt  = dbConnection.prepareStatement(sql))
        {
            // set the value
            int acctNumberInt = 0;
            try {
                acctNumberInt = Integer.parseInt(acctNumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }

            pstmt.setInt(1, acctNumberInt);
            pstmt.setString(2, pin);

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int checking_balance = rs.getInt("balance");
                int saving_balance = rs.getInt("balance_saving");
                return new BankAccount(id, name, checking_balance, saving_balance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public BankAccount createAccount(String name, String pin, int initialDeposit)
    {
        String accountNumber = "";

        String sql = "INSERT INTO accounts (name, pin, balance) VALUES(?, ?, ?)";
        try {
            try (PreparedStatement pstmt  = dbConnection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, pin);
                pstmt.setInt(3, initialDeposit);

                pstmt.executeUpdate();
                ResultSet genky = pstmt.getGeneratedKeys();
                accountNumber = Long.toString(genky.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return authenticateAccount(accountNumber, pin);
    }
}
