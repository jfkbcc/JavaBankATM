package edu.cuny.csi.csc330.bankatm;

import com.sun.xml.internal.ws.util.StringUtils;
import edu.cuny.csi.csc330.bankatm.BankAccount.AccountType;
import edu.cuny.csi.csc330.bankatm.panels.*;

import javax.swing.*;

public class Application extends JFrame
{
    final static String APP_TITLE = "Bank ATM";

    private Database db = new Database();
    private boolean authenticated = false;
    private BankAccount bankAccount = null;
    private AccountType activeAccountType = AccountType.None;

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
        activeAccountType = AccountType.None;
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
        activeAccountType = AccountType.None;

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

    private void displayDenomPanel(String type)
    {
        if (!(type.equals("withdraw") || type.equals("deposit"))
                || activeAccountType == AccountType.None) {
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
                    System.out.println("Insufficient funds for withdraw! Balance would be " + BankAccount.formatCurrency(predictedBalance));
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

                successMsg
                        .append(BankAccount.getAccountType(activeAccountType))
                        .append(" account. Available balance: ")
                        .append(bankAccount.getFormattedBalance(activeAccountType));

                JOptionPane.showMessageDialog(this, successMsg.toString());
            }

            displayAccountSelection();
        });
        this.setContentPanel(panel);
    }

    private void displayLoginDialog()
    {
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
                authenticated = true;

                System.out.println("Logged in to " + ba.getName());
                System.out.println("Balance: " + ba.getFormattedBalance(AccountType.Checking));

                displayAccountSelection();
            });
            setContentPanel(pinPanel);
        });
        setContentPanel(numberPanel);
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
