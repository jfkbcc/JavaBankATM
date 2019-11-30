package edu.cuny.csi.csc330.bankatm;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame
{
    private Database db = new Database();
    private boolean authenticated = false;
    private BankAccount bankAccount = null;

    public Application()
    {
        super("BankATM Machine");

        if (!db.open("bankdata")) {
            throw new RuntimeException("Failed to connect to SQLite");
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        // Add a label and a button
        this.add(new JLabel("Hello, world!"));

        JButton button = new JButton("Press me!");
        button.addActionListener(e -> JOptionPane.showMessageDialog(button, "Hello World!"));
        this.add(button);

        this.pack();

        this.logout();
    }

    public void logout() {
        authenticated = false;
        bankAccount = null;

        int dialogResult = JOptionPane.showConfirmDialog (this, "Are you an existing customer?","Existing Customer", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            displayLoginDialog();
        } else {
            displayNewCustomerDialog();
        }
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
            System.out.println("Authentication Failed");
            logout();
            return;
        }

        bankAccount = ba;
        authenticated = true;
        System.out.println("Logged in to " + ba.getName());
        System.out.println("Balance: " + ba.getFormattedBalance());
        this.setVisible(true);
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
