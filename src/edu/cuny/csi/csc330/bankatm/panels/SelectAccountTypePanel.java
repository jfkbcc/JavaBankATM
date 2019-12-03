package edu.cuny.csi.csc330.bankatm.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SelectAccountTypePanel extends JPanel
{
    private JButton btnCheckingAccount;
    private JButton btnSavingAccount;
    private JButton btnExit;

    public SelectAccountTypePanel()
    {
        super();

        setBounds(0, 0, 450, 300);
        setLayout(null);

        btnCheckingAccount = new JButton("Checkings Account");
        btnCheckingAccount.setBackground(UIManager.getColor("Button.background"));
        btnCheckingAccount.setForeground(new Color(0, 0, 128));
        btnCheckingAccount.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnCheckingAccount.setBounds(127, 72, 177, 44);
        btnCheckingAccount.setActionCommand("checking");
        this.add(btnCheckingAccount);

        btnSavingAccount = new JButton("Savings Account");
        btnSavingAccount.setForeground(new Color(0, 0, 128));
        btnSavingAccount.setBackground(UIManager.getColor("Button.background"));
        btnSavingAccount.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnSavingAccount.setBounds(127, 127, 177, 45);
        btnSavingAccount.setActionCommand("savings");
        this.add(btnSavingAccount);

        btnExit = new JButton("Quit");
        btnExit.setForeground(new Color(0, 0, 128));
        btnExit.setBackground(UIManager.getColor("Button.background"));
        btnExit.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnExit.setBounds(127, 183, 177, 44);
        this.add(btnExit);

        JLabel lblNewLabel = new JLabel("Select a Bank Account");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setBounds(10, 0, 414, 65);
        this.add(lblNewLabel);
    }

    public void setActionExit(ActionListener listener) {
        btnExit.addActionListener(listener);
    }

    public void setActionSelected(ActionListener listener) {
        btnCheckingAccount.addActionListener(listener);
        btnSavingAccount.addActionListener(listener);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectAccountTypePanel window = new SelectAccountTypePanel();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
