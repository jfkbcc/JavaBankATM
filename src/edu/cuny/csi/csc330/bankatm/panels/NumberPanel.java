package edu.cuny.csi.csc330.bankatm.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NumberPanel extends JPanel
{
    public interface UserCallback {
        void UserSelectedCb(String value);
    }

    UserCallback userCallback = null;

    JLabel lbLabel0;
    JTextField tf;
    JButton btButNum1;
    JButton btButNum2;
    JButton btButNum3;
    JButton btButNum4;
    JButton btButNum5;
    JButton btButNum6;
    JButton btButNum7;
    JButton btButNum8;
    JButton btButNum9;
    JButton btButNum0;
    JButton btButNumEnter;
    JButton btButNumDel;
    
    public NumberPanel(String text)
    {
        super();

        setBounds(0, 0, 450, 200);

        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        setLayout(gbPanel0);

        lbLabel0 = new JLabel(text);
        gbcPanel0.gridx = 6;
        gbcPanel0.gridy = 1;
        gbcPanel0.gridwidth = 2;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(lbLabel0, gbcPanel0);
        this.add(lbLabel0);

        tf = new JTextField();
        tf.setEditable(false);
        gbcPanel0.gridx = 2;
        gbcPanel0.gridy = 2;
        gbcPanel0.gridwidth = 8;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(tf, gbcPanel0);
        this.add(tf);

        btButNum1 = new JButton("1");
        gbcPanel0.gridx = 2;
        gbcPanel0.gridy = 6;
        gbcPanel0.gridwidth = 2;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum1, gbcPanel0);
        this.add(btButNum1);

        btButNum2 = new JButton("2");
        gbcPanel0.gridx = 6;
        gbcPanel0.gridy = 6;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum2, gbcPanel0);
        this.add(btButNum2);

        btButNum3 = new JButton("3");
        gbcPanel0.gridx = 9;
        gbcPanel0.gridy = 6;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum3, gbcPanel0);
        this.add(btButNum3);

        btButNum4 = new JButton("4");
        gbcPanel0.gridx = 2;
        gbcPanel0.gridy = 10;
        gbcPanel0.gridwidth = 2;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum4, gbcPanel0);
        this.add(btButNum4);

        btButNum5 = new JButton("5");
        gbcPanel0.gridx = 6;
        gbcPanel0.gridy = 10;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum5, gbcPanel0);
        this.add(btButNum5);

        btButNum6 = new JButton("6");
        gbcPanel0.gridx = 9;
        gbcPanel0.gridy = 10;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum6, gbcPanel0);
        this.add(btButNum6);

        btButNum7 = new JButton("7");
        gbcPanel0.gridx = 2;
        gbcPanel0.gridy = 13;
        gbcPanel0.gridwidth = 2;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum7, gbcPanel0);
        this.add(btButNum7);

        btButNum8 = new JButton("8" );
        gbcPanel0.gridx = 6;
        gbcPanel0.gridy = 13;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum8, gbcPanel0);
        this.add(btButNum8);

        btButNum9 = new JButton("9" );
        gbcPanel0.gridx = 9;
        gbcPanel0.gridy = 13;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum9, gbcPanel0);
        this.add(btButNum9);

        btButNum0 = new JButton("0" );
        gbcPanel0.gridx = 6;
        gbcPanel0.gridy = 16;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNum0, gbcPanel0);
        this.add(btButNum0);

        btButNumEnter = new JButton("Enter" );
        gbcPanel0.gridx = 9;
        gbcPanel0.gridy = 16;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNumEnter, gbcPanel0);
        this.add(btButNumEnter);

        btButNumDel = new JButton("Del" );
        gbcPanel0.gridx = 2;
        gbcPanel0.gridy = 16;
        gbcPanel0.gridwidth = 2;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(btButNumDel, gbcPanel0);
        this.add(btButNumDel);

        JButton buttons[] = {
                btButNum0,
                btButNum1, btButNum2, btButNum3,
                btButNum4, btButNum5, btButNum6,
                btButNum7, btButNum8, btButNum9
        };

        for (int i = 0; i < 10; i++) {
            buttons[i].setActionCommand(Integer.toString(i));
            buttons[i].addActionListener(this::onButtonKeyPressed);
        }

        btButNumDel.addActionListener(this::onButtonDelete);
        btButNumEnter.addActionListener(this::onButtonEnter);

        setVisible(true);
    }

    public String getValue() {
        return tf.getText();
    }

    private void onButtonKeyPressed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        tf.setText(tf.getText() + button.getActionCommand());
    }

    private void onButtonDelete(ActionEvent e) {
        String text = tf.getText();
        if (!text.isEmpty()) {
            tf.setText(text.substring(0, text.length() - 1));
        }
    }

    public void onButtonEnter(ActionEvent e) {
        if (userCallback != null)
            userCallback.UserSelectedCb(getValue());
    }

    public void setCallBack(UserCallback cb) {
        userCallback = cb;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NumberPanel window = new NumberPanel("Enter your Account Number");

                    JFrame frame = new JFrame();
                    frame.setVisible(false);
                    frame.setBounds(0, 0, 450, 300);
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
