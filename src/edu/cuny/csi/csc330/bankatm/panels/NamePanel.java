package edu.cuny.csi.csc330.bankatm.panels;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Font;

public class NamePanel extends JPanel {
	private String text = " ";

	public interface NamePanelCallback {
		void onSelected(String value);
	}

	NamePanelCallback userCallback = null;

	public void setCallBack(NamePanelCallback cb) {
		userCallback = cb;
	}

	public NamePanel() {
		setLayout(null);
		setBounds(0, 0, 450, 200);

		JLabel prompt = new JLabel("What is your name?");
		prompt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		prompt.setBounds(112, 0, 250, 71);
		this.add(prompt);

		JButton enter = new JButton("Enter");
		enter.setBounds(83, 132, 250, 40);
		this.add(enter);

		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(83, 68, 250, 35);
		this.add(textField);

		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text = textField.getText();
				if (userCallback != null)
					userCallback.onSelected(text);
			}
		});

	}

}
