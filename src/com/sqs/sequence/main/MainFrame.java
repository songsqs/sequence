package com.sqs.sequence.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2684471702993396821L;
	
	
	public MainFrame(String title){
		super(title);
		
		init();
	}
	
	private void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JPanel top=new JPanel();
		top.setBackground(Color.red);
		top.setPreferredSize(new Dimension(300, 100));
		
		JPanel bottom=new JPanel();
		bottom.setBackground(Color.blue);
		bottom.setPreferredSize(new Dimension(300, 300));
		
		this.getContentPane().add(top, BorderLayout.CENTER);
		this.getContentPane().add(bottom, BorderLayout.SOUTH);
	}
}
