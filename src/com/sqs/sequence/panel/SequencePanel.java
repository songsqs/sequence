package com.sqs.sequence.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
import com.sqs.sequence.enums.LifelineTypeEnum;
import com.sqs.sequence.enums.PositionEnum;
import com.sqs.sequence.server.Parser;
import com.sqs.sequence.utils.Pair;

public class SequencePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9219594350770048796L;

	private final static Parser PARSER = new Parser();

	private static final float[] arr = { 4.0f, 2.0f };
	private static final Stroke DEFAULT_DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_BEVEL, 1.0f, arr, 0);
	private static final int DEFAULT_TRIANGLE_LENGTH = 5;

	private Stroke dottedStroke = DEFAULT_DOTTED_STROKE;

	private int triangleLength = DEFAULT_TRIANGLE_LENGTH;

	private AffineTransform transform;
	private double scaleX = 1.0;
	private double scaleY = 1.0;

	// width height
	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 200;

	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	// font size
	private int defaultFontSize = 12;

	private volatile List<ObjectBean> objectBeanList;
	private volatile List<LifelineBean> lifelineBeanList;

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		// if (transform != null) {
		// g2d.setTransform(transform);
		// }

		if (objectBeanList == null || lifelineBeanList == null) {
			return;
		}

		drawSequenceFromBeans(g2d, objectBeanList, lifelineBeanList);

		System.out.println("Sequence widht:" + width + ",height:" + height);
	}

	public void drawSequence(String text){
		this.objectBeanList = null;
		this.lifelineBeanList = null;
		Pair<List<ObjectBean>, List<LifelineBean>> pair = PARSER.parse((Graphics2D) this.getGraphics(), text);
		this.objectBeanList = pair.getFirst();
		this.lifelineBeanList = pair.getSecond();
		resetSize();
		repaint();
	}

	/**
	 * 根据对象的大小充值size
	 */
	public void resetSize() {
		if (objectBeanList == null || objectBeanList.size() == 0) {
			return;
		}
		int tempWidth = 0, tempHeight = 0;
		for (ObjectBean objectBeanT : objectBeanList) {
			int beanWidth = objectBeanT.getX() + objectBeanT.getWidth();
			if (beanWidth > tempWidth) {
				tempWidth = beanWidth;
			}

			int beanHeight = objectBeanT.getHeight() + objectBeanT.getHeight() + objectBeanT.getLineLength();
			if (beanHeight > tempHeight) {
				tempHeight = beanHeight;
			}
		}

		this.width = (int) (tempWidth * scaleX + 1);
		this.height = (int) (tempHeight * scaleY + 1);

		this.setPreferredSize(new Dimension(width, height));
		this.revalidate();
	}

	private void drawSequenceFromBeans(Graphics2D graphics2d, List<ObjectBean> objectBeans,
			List<LifelineBean> lifelineBeans) {
		// draw objectbeans
		for (ObjectBean objectBeanT : objectBeans) {
			graphics2d.drawRect(objectBeanT.getX(), objectBeanT.getY(), objectBeanT.getWidth(),
					objectBeanT.getHeight());
			graphics2d.drawString(objectBeanT.getText(), objectBeanT.getTextX(), objectBeanT.getTextY());

			// draw line
			graphics2d.drawLine(objectBeanT.getLineX(), objectBeanT.getLineY(), objectBeanT.getLineX(),
					objectBeanT.getLineY() + objectBeanT.getLineLength());

			// draw end rec
			graphics2d.drawRect(objectBeanT.getX(),
					objectBeanT.getY() + objectBeanT.getLineLength() + objectBeanT.getHeight(), objectBeanT.getWidth(),
					objectBeanT.getHeight());

			// draw end string
			graphics2d.drawString(objectBeanT.getText(), objectBeanT.getTextX(),
					objectBeanT.getTextY() + objectBeanT.getLineLength() + objectBeanT.getHeight());
		}

		// draw lifelinebeans
		for (LifelineBean lifelineBeanT : lifelineBeans) {
			// draw line
			Stroke stroke = null;
			// 保存系统原有的stroke
			Stroke defauktStroke = graphics2d.getStroke();
			if (LifelineTypeEnum.SOLID_LINE.equals(lifelineBeanT.getType())) {
				// 实线
				stroke = defauktStroke;
			} else {
				// 虚线
				stroke = dottedStroke;
			}
			graphics2d.setStroke(stroke);

			if (lifelineBeanT.getFrom().equals(lifelineBeanT.getTo())) {
				// 如果开头和结尾都指向同一个ObjectBean,需要画三条线
				graphics2d.drawLine(lifelineBeanT.getStartX(), lifelineBeanT.getStartY(),
						lifelineBeanT.getEndX(), lifelineBeanT.getStartY());
				graphics2d.drawLine(lifelineBeanT.getEndX(), lifelineBeanT.getStartY(), lifelineBeanT.getEndX(),
						lifelineBeanT.getEndY());
				graphics2d.drawLine(lifelineBeanT.getStartX(), lifelineBeanT.getEndY(), lifelineBeanT.getEndX(),
						lifelineBeanT.getEndY());

			} else {
				graphics2d.drawLine(lifelineBeanT.getStartX(), lifelineBeanT.getStartY(), lifelineBeanT.getEndX(),
						lifelineBeanT.getEndY());
			}

			// 设置回系统原有的stroke
			graphics2d.setStroke(defauktStroke);

			// 画箭头
			if (PositionEnum.END.equals(lifelineBeanT.getTrianglePosition())) {
				int line1X = lifelineBeanT.getEndX() - triangleLength;
				int line1y = lifelineBeanT.getEndY() - triangleLength;
				graphics2d.drawLine(lifelineBeanT.getEndX(), lifelineBeanT.getEndY(), line1X, line1y);

				int line2x = lifelineBeanT.getEndX() - triangleLength;
				int line2y = lifelineBeanT.getEndY() + triangleLength;
				graphics2d.drawLine(lifelineBeanT.getEndX(), lifelineBeanT.getEndY(), line2x, line2y);
			} else if (lifelineBeanT.getFrom().equals(lifelineBeanT.getTo())) {
				// 指向自己
				int pointX = lifelineBeanT.getStartX();
				int pointY = lifelineBeanT.getEndY();

				int line1x = pointX + triangleLength;
				int line1y = pointY - triangleLength;
				graphics2d.drawLine(pointX, pointY, line1x, line1y);

				int line2x = pointX + triangleLength;
				int line2y = pointY + triangleLength;
				graphics2d.drawLine(pointX, pointY, line2x, line2y);
			} else {
				int line1x = lifelineBeanT.getStartX() + triangleLength;
				int line1y = lifelineBeanT.getStartY() - triangleLength;
				graphics2d.drawLine(lifelineBeanT.getStartX(), lifelineBeanT.getStartY(), line1x, line1y);

				int line2x = lifelineBeanT.getStartX() + triangleLength;
				int line2y = lifelineBeanT.getStartY() + triangleLength;
				graphics2d.drawLine(lifelineBeanT.getStartX(), lifelineBeanT.getStartY(), line2x, line2y);
			}

			// draw text
			graphics2d.drawString(lifelineBeanT.getText(), lifelineBeanT.getTextX(), lifelineBeanT.getTextY());

		}
	}

	public Stroke getDottedStroke() {
		return dottedStroke;
	}

	public void setDottedStroke(Stroke dottedStroke) {
		this.dottedStroke = dottedStroke;
	}

	public int getTriangleLength() {
		return triangleLength;
	}

	public void setTriangleLength(int triangleLength) {
		this.triangleLength = triangleLength;
	}

	public void setTransform(double sx, double sy) {
		this.scaleX = sx;
		this.scaleY = sy;
		this.resetSize();
		this.transform = AffineTransform.getScaleInstance(sx, sy);
	}

	public int getDefaultFontSize() {
		return defaultFontSize;
	}

	public void setDefaultFontSize(int defaultFontSize) {
		this.defaultFontSize = defaultFontSize;
	}

	public static void main(String[] args){
		JFrame jf=new JFrame();
		SequencePanel sp = new SequencePanel();
		sp.setPreferredSize(new Dimension(300, 300));
		sp.setBackground(Color.white);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(sp);
		jf.setVisible(true);
		jf.pack();

		Parser parser = new Parser();
		String testInput = "A->B:lsllsl\nA->C:dsdsds";
		Pair<List<ObjectBean>, List<LifelineBean>> pair = parser.parse((Graphics2D) sp.getGraphics(),
				testInput);
		for (ObjectBean objectBean : pair.getFirst()) {
			System.out.println(objectBean);
		}
		System.out.println("-------------------");
		for (LifelineBean lifelineBean : pair.getSecond()) {
			System.out.println(lifelineBean);
		}

		sp.drawSequence(testInput);
	}

}
