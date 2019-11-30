package edu.cuny.csi.csc330.bankatm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Application extends JFrame
{
    public Application() {
        super("Hello");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        // Add a label and a button
        this.add(new JLabel("Hello, world!"));

        JButton button = new JButton("Press me!");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(button, "Hello World!");
            }
        });
        this.add(button);

        // Arrange the components inside the window
        this.pack();

        // By default, the window is not visible. Make it visible.
        this.setVisible(true);
    }

    public static void main(final String[] args) {
//        Application app = new Application();

        Database db = new Database();
        if (db.open("bankdata")) {
            System.out.println("Bank data: ");

            ArrayList<BankAccount> accts = db.getAccounts();
            System.out.println("--SEP--");
            for (BankAccount acct : accts) {
                System.out.printf("Account of %s (id #%d) balance: %s\n", acct.getName(), acct.getId(), acct.getFormattedBalance());
            }

            BankAccount ba = db.authenticateAccount(1, "0123");
            System.out.println("Test Authentication: " + (ba == null ? "Failed" : ba.getName() + " authenticated"));
        }
    }
}
