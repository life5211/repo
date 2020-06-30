package com.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("1-label");
		lblNewLabel.setBounds(24, 25, 54, 15);
		panel.add(lblNewLabel);

		text1 = new JTextField();
		text1.setBounds(88, 22, 66, 21);
		panel.add(text1);
		text1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("2-label");
		lblNewLabel_1.setBounds(24, 60, 54, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("3-label");
		lblNewLabel_2.setBounds(24, 102, 54, 15);
		panel.add(lblNewLabel_2);

		text2 = new JTextField();
		text2.setBounds(88, 60, 66, 21);
		panel.add(text2);
		text2.setColumns(10);

		text3 = new JTextField();
		text3.setBounds(88, 99, 66, 21);
		panel.add(text3);
		text3.setColumns(10);

		textArea = new JTextArea();
		textArea.setBounds(198, 21, 188, 138);
		panel.add(textArea);

		JButton btnNewButton = new JButton("button");
		btnNewButton.setBounds(45, 130, 93, 23);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new MyActionLitener());
	}

	class MyActionLitener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str1 = text1.getText();
			String str2 = text2.getText();
			String str3 = text3.getText();

			textArea.setText(str1 + str2 + str3);

		}

	}
}
