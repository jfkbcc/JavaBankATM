package edu.cuny.csi.csc330.bankatm;

import com.sun.xml.internal.ws.util.StringUtils;
import edu.cuny.csi.csc330.bankatm.panels.*;

import javax.swing.*;

public class Application extends JFrame {
	final static String APP_TITLE = "Bank ATM";

	private Database db = new Database();
	private BankAccount bankAccount = null;
	private BankAccountType activeAccountType = BankAccountType.None;

	private Application() {
		super(APP_TITLE);

		if (!db.open("bankdata")) {
			throw new RuntimeException("Failed to connect to SQLite");
		}

		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		this.setResizable(false);

		displayStartScreen();

		this.setVisible(true);
	}

	private void setContentPanel(JPanel panel) {
		this.getContentPane().removeAll();

		this.add(panel);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	public void logout() {
		activeAccountType = BankAccountType.None;
		bankAccount = null;

		displayStartScreen();
	}

	private void displayStartScreen() {
		LoginPanel panel = new LoginPanel();
		panel.setActionExistingCustomer(e -> displayLoginDialog());
		panel.setActionNewCustomer(e -> displayNewCustomerDialog());

		this.setContentPanel(panel);
	}

	private void displayAccountSelection() {
		activeAccountType = BankAccountType.None;

		SelectAccountTypePanel panel = new SelectAccountTypePanel();
		panel.setActionExit(e -> logout());
		panel.setActionSelected(e -> {
			String selected = e.getActionCommand();
			displayBankScreen(BankAccountType.getAccountType(selected));
		});

		this.setContentPanel(panel);
	}

	private void displayBankScreen(BankAccountType accountType) {
		if (accountType == BankAccountType.None) {
			logout();
			System.err.println("Invalid bank account type `" + accountType.toString()
					+ "`, valid account types: checking, savings");
			return;
		}

		if (bankAccount == null) {
			logout();
			return;
		}

		activeAccountType = accountType;

		BankAccountPanel panel = new BankAccountPanel(bankAccount, accountType);
		panel.setActionExit(e -> displayAccountSelection());
		panel.setActionSelected(e -> {
			String cmdType = e.getActionCommand();

			System.out.println("Action `" + cmdType + "` button clicked!");
			displayDenomPanel(cmdType);
		});
		this.setContentPanel(panel);
	}

	private void displayDenomPanel(String type) {
		if (!(type.equals("withdraw") || type.equals("deposit")) || activeAccountType == BankAccountType.None) {
			logout();
			return;
		}

		boolean isWithdraw = type.equals("withdraw");
		String ucType = StringUtils.capitalize(type);

		DenominationPanel panel = new DenominationPanel(ucType + " Amount");
		panel.setCallBack(value -> {
			int currency = value * 100;
			String formattedCurrency = BankAccount.formatCurrency(currency);

			System.out.println(ucType + " " + formattedCurrency + " from " + bankAccount.getName());

			if (isWithdraw) {
				currency = -currency;
			}

			BankAccount updatedAccount = db.adjustBalance(bankAccount.getId(), activeAccountType, currency);
			if (updatedAccount == null) {
				// Insufficient funds?
				int predictedBalance = bankAccount.getBalance(activeAccountType) + currency;
				if (predictedBalance < 0) {
					JOptionPane.showMessageDialog(this, "Insufficient funds for withdrawal!");
					System.out.println("Insufficient funds for withdraw! Balance would be "
							+ BankAccount.formatCurrency(predictedBalance));
				}
			} else {
				// Success, maybe display a popup message
				bankAccount = updatedAccount;

				StringBuilder successMsg = new StringBuilder();
				if (isWithdraw) {
					successMsg.append("You withdraw ").append(formattedCurrency).append(" from your ");
				} else {
					successMsg.append("You deposited ").append(formattedCurrency).append(" into your ");
				}

				successMsg.append(BankAccountType.getAccountType(activeAccountType))
						.append(" account. Available balance: ")
						.append(bankAccount.getFormattedBalance(activeAccountType));

				JOptionPane.showMessageDialog(this, successMsg.toString());
			}

			displayAccountSelection();
		});
		this.setContentPanel(panel);
	}

	private void displayLoginDialog() {
		NumberPanel numberPanel = new NumberPanel("Enter your Account Number");
		numberPanel.setCallBack((accountNumber) -> {
			if (accountNumber.isEmpty()) {
				logout();
				return;
			}

			NumberPanel pinPanel = new NumberPanel("Enter your Pin Number");
			pinPanel.setCallBack((pinNumber) -> {
				BankAccount ba = db.authenticateAccount(accountNumber, pinNumber);
				if (ba == null) {
					JOptionPane.showMessageDialog(this, "Authentication failed, logging out");
					logout();
					return;
				}

				bankAccount = ba;

				System.out.println("Logged in to " + ba.getName());
				System.out.println("Balance: " + ba.getFormattedBalance(BankAccountType.Checking));

				displayAccountSelection();
			});
			setContentPanel(pinPanel);
		});
		setContentPanel(numberPanel);
	}

	/*
	 * Creates account with given name,pin,deposit inserts data in database file,
	 * returns user to login screen
	 */
	private void displayNewCustomerDialog() {

		NamePanel namePanel = new NamePanel();
		setContentPanel(namePanel);
		namePanel.setCallBack((name) -> {

			NumberPanel pinPanel = new NumberPanel("Set a Pin Number");
			setContentPanel(pinPanel);
			pinPanel.setCallBack((pinNumber) -> {

				DenominationPanel depPanel = new DenominationPanel("Initial Deposit Amount");
				setContentPanel(depPanel);

				depPanel.setCallBack((deposit) -> {
					BankAccount ba = db.createAccount(name, pinNumber, deposit * 100);
					bankAccount = ba;
					JOptionPane.showMessageDialog(this, "Account created for: " + name + "\nInitial deposit: $"
							+ deposit + "\nYour account number is: " + db.getNumAccounts());

					displayStartScreen();
					return;
				});

			});

		});

	}

	public static void main(final String[] args) {
		Application app = new Application();
	}
}
