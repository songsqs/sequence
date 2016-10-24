package com.sqs.sequence.panel;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SequencePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9219594350770048796L;

	private String text;

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		String abc = "a";
		g2d.drawString(abc, 0, 20);
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D rec = fm.getStringBounds(abc, g2d);
		g2d.drawRect(0, 30, (int) rec.getWidth(), (int) rec.getHeight());
		System.out.println("a,width:" + rec.getWidth() + ",height:" + rec.getHeight());

		String test = "中服务不可用修复数据\n这是一个测试消息";
		g2d.drawString(test, 0, 70);
		rec = fm.getStringBounds(test, g2d);
		g2d.drawRect(0, 90, (int) rec.getWidth(), (int) rec.getHeight());
		System.out.println("t,width:" + rec.getWidth() + ",height:" + rec.getHeight());

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.repaint();
	}
	
	public static void main(String[] args){
		JFrame jf=new JFrame();
		SequencePanel sp = new SequencePanel();
		sp.setPreferredSize(new Dimension(300, 300));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(sp);
		jf.setVisible(true);
		jf.pack();
	}

}
