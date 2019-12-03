package edu.cuny.csi.csc330.bankatm.panels;

import edu.cuny.csi.csc330.bankatm.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BankAccountPanel extends JPanel
{
    private JButton btnWithdraw;
    private JButton btnDeposit;
    private JButton btnExit;

    public BankAccountPanel(BankAccount bankAccount, String accountType)
    {
        super();

        setBounds(0, 0, 450, 300);
        setLayout(null);

        btnWithdraw = new JButton("Withdraw");
        btnWithdraw.setBackground(UIManager.getColor("Button.background"));
        btnWithdraw.setForeground(new Color(0, 0, 128));
        btnWithdraw.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnWithdraw.setBounds(127, 72, 177, 44);
        btnWithdraw.setActionCommand("withdraw");
        this.add(btnWithdraw);

        btnDeposit = new JButton("Deposit");
        btnDeposit.setForeground(new Color(0, 0, 128));
        btnDeposit.setBackground(UIManager.getColor("Button.background"));
        btnDeposit.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnDeposit.setBounds(127, 127, 177, 45);
        btnDeposit.setActionCommand("deposit");
        this.add(btnDeposit);

        btnExit = new JButton("Quit");
        btnExit.setForeground(new Color(0, 0, 128));
        btnExit.setBackground(UIManager.getColor("Button.background"));
        btnExit.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnExit.setBounds(127, 183, 177, 44);
        this.add(btnExit);

        JLabel lblNewLabel = new JLabel("Bank Account for " + bankAccount.getName());
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setBounds(10, 0, 414, 65);
        this.add(lblNewLabel);

        JLabel lblNewLabel2 = new JLabel("Available Balance: " + bankAccount.getFormattedBalance());
        lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel2.setBounds(10, 45, 414, 25);
        this.add(lblNewLabel2);
    }

    public void setActionExit(ActionListener listener) {
        btnExit.addActionListener(listener);
    }

    public void setActionSelected(ActionListener listener) {
        btnDeposit.addActionListener(listener);
        btnWithdraw.addActionListener(listener);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BankAccount ba = new BankAccount(1, "joey", 1500);

                    BankAccountPanel window = new BankAccountPanel(ba, "checking");

                    JFrame frame = new JFrame();
                    frame.setBounds(100, 100, 450, 300);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().setLayout(null);

                    frame.add(window);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

