package com.sqs.sequence.bean;

/**
 * 时序图对象bean
 * 
 * @author sqs
 *
 */
public class ObjectBean {

	/**
	 * 对象名称
	 */
	private String text;

	/**
	 * 对象起始x坐标
	 */
	private int x;

	/**
	 * 对象起始y坐标
	 */
	private int y;

	/**
	 * 对象宽度
	 */
	private int width;

	/**
	 * 对象高度
	 */
	private int height;

	/**
	 * 字符起始x坐标
	 */
	private int textX;

	/**
	 * 字符起始y坐标
	 */
	private int textY;

	/**
	 * 对象轴线起始x坐标
	 */
	private int lineX;

	/**
	 * 对象轴线起始y坐标
	 */
	private int lineY;

	/**
	 * 对象轴线高度
	 */
	private int lineLength;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextX() {
		return textX;
	}

	public void setTextX(int textX) {
		this.textX = textX;
	}

	public int getTextY() {
		return textY;
	}

	public void setTextY(int textY) {
		this.textY = textY;
	}

	public int getLineX() {
		return lineX;
	}

	public void setLineX(int lineX) {
		this.lineX = lineX;
	}

	public int getLineY() {
		return lineY;
	}

	public void setLineY(int lineY) {
		this.lineY = lineY;
	}

	public int getLineLength() {
		return lineLength;
	}

	public void setLineLength(int lineLength) {
		this.lineLength = lineLength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + lineLength;
		result = prime * result + lineX;
		result = prime * result + lineY;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectBean other = (ObjectBean) obj;
		if (height != other.height)
			return false;
		if (lineLength != other.lineLength)
			return false;
		if (lineX != other.lineX)
			return false;
		if (lineY != other.lineY)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
