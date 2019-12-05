package edu.cuny.csi.csc330.bankatm;

import edu.cuny.csi.csc330.bankatm.BankAccount.AccountType;
import edu.cuny.csi.csc330.bankatm.panels.BankAccountPanel;
import edu.cuny.csi.csc330.bankatm.panels.SelectAccountTypePanel;
import edu.cuny.csi.csc330.bankatm.panels.LoginPanel;

import javax.swing.*;

public class Application extends JFrame
{
    final static String APP_TITLE = "Bank ATM";

    private Database db = new Database();
    private boolean authenticated = false;
    private BankAccount bankAccount = null;

    private Application()
    {
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
        authenticated = false;
        bankAccount = null;

        displayStartScreen();
    }

    private void displayStartScreen()
    {
        LoginPanel panel = new LoginPanel();
        panel.setActionExistingCustomer(e -> {
            displayLoginDialog();
        });
        panel.setActionNewCustomer(e -> {
            displayNewCustomerDialog();
        });

        this.setContentPanel(panel);
    }

    private void displayAccountSelection()
    {
        SelectAccountTypePanel panel = new SelectAccountTypePanel();
        panel.setActionExit(e -> logout());
        panel.setActionSelected(e -> {
            String selected = e.getActionCommand();
            displayBankScreen(BankAccount.getAccountType(selected));
        });

        this.setContentPanel(panel);
    }

    private void displayBankScreen(AccountType accountType)
    {
        if (accountType == AccountType.None) {
            logout();
            System.err.println("Invalid bank account type `" + accountType.toString() + "`, valid account types: checking, savings");
            return;
        }

        if (bankAccount == null) {
            logout();
            return;
        }

        BankAccountPanel panel = new BankAccountPanel(bankAccount, accountType);
        panel.setActionExit(e -> displayAccountSelection());
        panel.setActionSelected(e -> {
            String cmdType = e.getActionCommand();
            System.out.println("Action `" + cmdType + "` button clicked!");
        });
        this.setContentPanel(panel);
    }

    private void displayLoginDialog()
    {
        String accountNumber = NumberDialog.showDialog("Account Number", "Enter your Account Number");
        if (accountNumber.isEmpty()) {
            logout();
            return;
        }

        String pinNumber = NumberDialog.showDialog("Pin Number", "Enter your Pin Number");

        BankAccount ba = db.authenticateAccount(accountNumber, pinNumber);
        if (ba == null) {
            JOptionPane.showMessageDialog(this, "Authentication failed, logging out");
            logout();
            return;
        }

        bankAccount = ba;
        authenticated = true;

        System.out.println("Logged in to " + ba.getName());
        System.out.println("Balance: " + ba.getFormattedBalance(AccountType.Checking));

        displayAccountSelection();
    }

    private void displayNewCustomerDialog()
    {
        // TODO(*): needs to be implemented

        // BankAccount bankAcct = db.createAccount("test", "0123", 1795);
    }

    public static void main(final String[] args) {
        Application app = new Application();
    }
}
