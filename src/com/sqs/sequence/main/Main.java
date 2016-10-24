package com.sqs.sequence.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Main {

	public static void main(String[] args) {
		JFrame jFrame = new JFrame("JtextArea");

		JTextArea textArea = new JTextArea(10, 30);
		jFrame.getContentPane().setLayout(null);
		textArea.setBounds(10, 10, 100, 100);

		jFrame.getContentPane().add(textArea);
		jFrame.setSize(200, 200);

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);
		// jFrame.pack();
		
		JButton button = new JButton("Get");
		button.setBounds(10, 120, 30, 30);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getText();

				System.out.println(Arrays.asList(text.split("\n")));
			}
		});
		
		jFrame.getContentPane().add(button);
	}

}
