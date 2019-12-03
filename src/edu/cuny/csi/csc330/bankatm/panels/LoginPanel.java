package edu.cuny.csi.csc330.bankatm.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel
{
    private JButton btnExistingCustomer;
    private JButton btnNewCustomer;

    public LoginPanel()
    {
        super();

        setBounds(0, 0, 450, 300);
        setLayout(null);

        btnExistingCustomer = new JButton("Existing Customer");
        btnExistingCustomer.setBackground(UIManager.getColor("Button.background"));
        btnExistingCustomer.setForeground(new Color(0, 0, 128));
        btnExistingCustomer.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnExistingCustomer.setBounds(127, 72, 177, 44);
        this.add(btnExistingCustomer);

        btnNewCustomer = new JButton("New Customer");
        btnNewCustomer.setForeground(new Color(0, 0, 128));
        btnNewCustomer.setBackground(UIManager.getColor("Button.background"));
        btnNewCustomer.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnNewCustomer.setBounds(127, 127, 177, 45);
        this.add(btnNewCustomer);

        JLabel lblNewLabel = new JLabel("Welcome to CSC330 ATM");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setBounds(10, 0, 414, 65);
        this.add(lblNewLabel);
    }

    public void setActionExistingCustomer(ActionListener listener) {
        btnExistingCustomer.addActionListener(listener);
    }

    public void setActionNewCustomer(ActionListener listener) {
        btnNewCustomer.addActionListener(listener);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPanel window = new LoginPanel();

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
