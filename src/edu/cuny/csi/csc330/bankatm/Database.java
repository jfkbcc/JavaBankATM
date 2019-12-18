package edu.cuny.csi.csc330.bankatm;

import java.sql.*;
import java.util.ArrayList;

public class Database {
	private Connection dbConnection = null;
	private String dbName = "";

	public Database() {

	}

	/**
	 * Close database connection
	 */
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

	/**
	 * Opens an sqlite database with the filename (automatically appends .db)
	 *
	 * @param databaseName database_name (excluding .db)
	 * @return success
	 */
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

	/**
	 * Check if the database connection has been closed
	 *
	 * @return true if not connected
	 */
	public boolean isClosed() {
		return (dbConnection == null);
	}

	/**
	 * Get an array of BankAccounts in the database
	 *
	 * @return BankAccount[]
	 */
	public ArrayList<BankAccount> getAccounts() {
		String sql = "SELECT id, name, balance, balance_saving FROM accounts WHERE id > ?";

		ArrayList<BankAccount> bankAccounts = new ArrayList<>();

		try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
			// set the value
			pstmt.setInt(1, 0);

			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int checking_balance = rs.getInt("balance");
				int saving_balance = rs.getInt("balance_saving");

				BankAccount bankAccount = new BankAccount(id, name, checking_balance, saving_balance);
				bankAccounts.add(bankAccount);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return bankAccounts;
	}

	/**
	 * Authenticate a bank account with the given account number and pin
	 *
	 * @param acctNumber id
	 * @param pin pin
	 * @return BankAccount
	 */
	public BankAccount authenticateAccount(String acctNumber, String pin) {
		String sql = "SELECT id, name, balance, balance_saving FROM accounts WHERE id = ? and pin = ?";

		try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
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

			ResultSet rs = pstmt.executeQuery();

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

	/**
	 * Get a bank account by the specified account number
	 *
	 * @param accountNumber id
	 * @return BankAccount
	 */
	public BankAccount getBankAccount(int accountNumber) {
		String sql = "SELECT id, name, balance, balance_saving FROM accounts WHERE id = ?";

		try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
			pstmt.setInt(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();

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

	/**
	 * Create a new bank account with the given name, pin, and initial deposit
	 *
	 * @param name full name
	 * @param pin pin
	 * @param initialDeposit initial deposit
	 * @return BankAccount
	 */
	public BankAccount createAccount(String name, String pin, int initialDeposit) {
		String accountNumber = "";

		String sql = "INSERT INTO accounts (id, name, pin, balance, balance_saving) VALUES(?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
			// not sure why this was changed, but the database should of been auto-incrementing id
			pstmt.setInt(1, getNumAccounts() + 1);
			pstmt.setString(2, name);
			pstmt.setString(3, pin);
			pstmt.setInt(4, initialDeposit);
			pstmt.setInt(5, 0);

			pstmt.executeUpdate();
			ResultSet genky = pstmt.getGeneratedKeys();
			accountNumber = Long.toString(genky.getLong(1));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return authenticateAccount(accountNumber, pin);
	}

	/**
	 * Update the currency in a checking or savings account
	 *
	 * @param accountNumber int
	 * @param accountType   BankAccountType
	 * @param currency      REMEMBER - this number is an integer, 1.00 is
	 *                      represented as 100.
	 *
	 * @return true/false
	 */
	private boolean updateMoneyForAccount(int accountNumber, BankAccountType accountType, int currency) {
		String columnName = (accountType == BankAccountType.Checking ? "balance" : "balance_saving");

		String sql = "UPDATE accounts SET " + columnName + " = ? WHERE id = ?";
		try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
			pstmt.setInt(1, currency);
			pstmt.setInt(2, accountNumber);

			int rowsUpdated = pstmt.executeUpdate();
			return (rowsUpdated == 1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	/**
	 * The only public method to adjust funds on a users account, does not allow directly setting
	 * the value. Instead it only increments or decrements a value from the existing balance.
	 *
	 * @param accountNumber int
	 * @param accountType   BankAccountType
	 * @param currency      REMEMBER - this number is an integer, 1.00 is
	 *                      represented as 100.
	 *
	 * @return updated bank account if changed, otherwise null
	 */
	public BankAccount adjustBalance(int accountNumber, BankAccountType accountType, int currency) {
		BankAccount latestAccount = getBankAccount(accountNumber);
		int cur_balance = latestAccount.getBalance(accountType);
		int new_balance = cur_balance + currency;

		// currency > 0 -> deposit
		// currency < 0 -> withdraw
		if (currency > 0 || (currency < 0 && new_balance > 0)) {
			boolean success = updateMoneyForAccount(accountNumber, accountType, new_balance);
			if (success) {
				return getBankAccount(accountNumber);
			}
		}

		return null;
	}

	public static void main(final String[] args) {
		Database db = new Database();
		if (!db.open("bankdata")) {
			throw new RuntimeException("Failed to connect to SQLite");
		}

		System.out.println("Accounts in database:");
		for (BankAccount account : db.getAccounts()) {
			System.out.println(account.getId() + "\t\t" + account.getName() + "\t\t"
					+ account.getFormattedBalance(BankAccountType.Checking) + "\t\t"
					+ account.getFormattedBalance(BankAccountType.Savings));
		}
	}

	public int getNumAccounts() {
		return getAccounts().size();
	}
}
