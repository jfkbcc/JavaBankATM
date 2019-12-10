package edu.cuny.csi.csc330.bankatm.panels;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;

public class DenominationPanel extends JPanel
{
    class NumberFormatterMod extends NumberFormatter
    {
        public NumberFormatterMod(NumberFormat format) {
            super(format);
        }

        public Object stringToValue(String text) throws ParseException {
            if (text.isEmpty())
                return 0;
            return super.stringToValue(text);
        }
    }

    public interface DenomPanelCallback {
        void onSelected(int currency);
    }

    private NumberFormatter numberFormatter;
    private JFormattedTextField textField;
    private DenomPanelCallback userCallback;

    public DenominationPanel(String text)
    {
        super();

        setBounds(0, 0, 450, 300);
        setLayout(null);

        JButton btnNewButton = new JButton("$1");
        btnNewButton.setBackground(UIManager.getColor("Button.background"));
        btnNewButton.setForeground(new Color(0, 0, 128));
        btnNewButton.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnNewButton.setBounds(117, 74, 74, 29);
        btnNewButton.setActionCommand("1");
        btnNewButton.addActionListener(this::onIncrementCurrency);
        this.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("$5");
        btnNewButton_1.setForeground(new Color(0, 0, 128));
        btnNewButton_1.setBackground(UIManager.getColor("Button.background"));
        btnNewButton_1.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnNewButton_1.setBounds(117, 114, 74, 29);
        btnNewButton_1.setActionCommand("5");
        btnNewButton_1.addActionListener(this::onIncrementCurrency);
        this.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("$10");
        btnNewButton_2.setForeground(new Color(0, 0, 128));
        btnNewButton_2.setBackground(UIManager.getColor("Button.background"));
        btnNewButton_2.setFont(new Font("DialogInput", Font.BOLD, 14));
        btnNewButton_2.setBounds(117, 154, 74, 29);
        btnNewButton_2.setActionCommand("10");
        btnNewButton_2.addActionListener(this::onIncrementCurrency);
        this.add(btnNewButton_2);

        JLabel lblNewLabel = new JLabel(text);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblNewLabel.setBounds(10, 11, 414, 44);
        this.add(lblNewLabel);

        JButton button = new JButton("$100");
        button.setForeground(new Color(0, 0, 128));
        button.setFont(new Font("DialogInput", Font.BOLD, 14));
        button.setBackground(UIManager.getColor("Button.background"));
        button.setBounds(224, 154, 74, 29);
        button.setActionCommand("100");
        button.addActionListener(this::onIncrementCurrency);
        this.add(button);

        JButton button_1 = new JButton("$50");
        button_1.setForeground(new Color(0, 0, 128));
        button_1.setFont(new Font("DialogInput", Font.BOLD, 14));
        button_1.setBackground(UIManager.getColor("Button.background"));
        button_1.setBounds(224, 114, 74, 29);
        button_1.setActionCommand("50");
        button_1.addActionListener(this::onIncrementCurrency);
        this.add(button_1);

        JButton button_2 = new JButton("$20");
        button_2.setForeground(new Color(0, 0, 128));
        button_2.setFont(new Font("DialogInput", Font.BOLD, 14));
        button_2.setBackground(UIManager.getColor("Button.background"));
        button_2.setBounds(224, 74, 74, 29);
        button_2.setActionCommand("20");
        button_2.addActionListener(this::onIncrementCurrency);
        this.add(button_2);

        JLabel lblNewLabel_1 = new JLabel("Enter Exact Amount");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 14));
        lblNewLabel_1.setBounds(127, 194, 181, 22);
        this.add(lblNewLabel_1);

        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        numberFormatter = new NumberFormatterMod(longFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);

        textField = new JFormattedTextField(numberFormatter);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBounds(167, 225, 96, 25);
        textField.setColumns(10);
        textField.setText("0");
        this.add(textField);

        JButton btnNewButton_3 = new JButton("Enter");
        btnNewButton_3.setForeground(new Color(0, 0, 0));
        btnNewButton_3.setFont(new Font("Dialog", Font.BOLD, 14));
        btnNewButton_3.setBounds(270, 226, 74, 23);
        btnNewButton_3.addActionListener(this::onButtonEnter);
        this.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Clear");
        btnNewButton_4.setForeground(new Color(0, 0, 0));
        btnNewButton_4.setFont(new Font("Dialog", Font.BOLD, 14));
        btnNewButton_4.setBounds(90, 226, 74, 23);
        btnNewButton_4.addActionListener(this::onButtonClear);
        this.add(btnNewButton_4);
    }

    private int getValue() {
        try {
            return (int)numberFormatter.stringToValue(textField.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void onButtonClear(ActionEvent event) {
        textField.setText("0");
    }

    public void onButtonEnter(ActionEvent e) {
        if (userCallback != null) {
            userCallback.onSelected(this.getValue());
        }
    }

    private void onIncrementCurrency(ActionEvent event) {
        try {
            int increment = Integer.parseInt(event.getActionCommand());
            int val = this.getValue();

            textField.setText(String.valueOf(val + increment));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void setCallBack(DenomPanelCallback cb) {
        this.userCallback = cb;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DenominationPanel window = new DenominationPanel("Withdraw Amount");

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
