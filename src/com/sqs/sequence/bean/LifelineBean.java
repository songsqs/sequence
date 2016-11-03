package com.sqs.sequence.bean;

import com.sqs.sequence.enums.LifelineTypeEnum;
import com.sqs.sequence.enums.PositionEnum;

/**
 * 时序图生命线bean
 * 
 * @author sqs
 *
 */
public class LifelineBean {

	/**
	 * 生命线的消息
	 */
	private String text;

	/**
	 * 线起始x坐标
	 */
	private int startX;

	/**
	 * 线起始y坐标
	 */
	private int startY;

	/**
	 * 线结束x坐标
	 */
	private int endX;

	/**
	 * 线结束y坐标
	 */
	private int endY;

	/**
	 * 消息字符串起始x坐标
	 */
	private int textX;

	/**
	 * 消息字符串起始y坐标
	 */
	private int textY;

	/**
	 * 占用的横向(x轴)长度
	 */
	private int width;

	/**
	 * 占用的纵向(y轴)长度
	 */
	private int height;

	/**
	 * text占用宽度
	 */
	private int textWidth;

	/**
	 * text占用的高度
	 */
	private int textHeight;

	/**
	 * 生命线起始对象
	 */
	private ObjectBean from;

	/**
	 * 生命线结束对象
	 */
	private ObjectBean to;

	/**
	 * 线属性(实线 or 虚线)
	 */
	private LifelineTypeEnum type;

	/**
	 * 箭头位置(head or end)
	 */
	private PositionEnum trianglePosition;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
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

	public int getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(int textWidth) {
		this.textWidth = textWidth;
	}

	public int getTextHeight() {
		return textHeight;
	}

	public void setTextHeight(int textHeight) {
		this.textHeight = textHeight;
	}

	public ObjectBean getFrom() {
		return from;
	}

	public void setFrom(ObjectBean from) {
		this.from = from;
	}

	public ObjectBean getTo() {
		return to;
	}

	public void setTo(ObjectBean to) {
		this.to = to;
	}

	public LifelineTypeEnum getType() {
		return type;
	}

	public void setType(LifelineTypeEnum type) {
		this.type = type;
	}

	public PositionEnum getTrianglePosition() {
		return trianglePosition;
	}

	public void setTrianglePosition(PositionEnum trianglePosition) {
		this.trianglePosition = trianglePosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endX;
		result = prime * result + endY;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + height;
		result = prime * result + startX;
		result = prime * result + startY;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + textHeight;
		result = prime * result + textWidth;
		result = prime * result + textX;
		result = prime * result + textY;
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((trianglePosition == null) ? 0 : trianglePosition.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + width;
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
		LifelineBean other = (LifelineBean) obj;
		if (endX != other.endX)
			return false;
		if (endY != other.endY)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (height != other.height)
			return false;
		if (startX != other.startX)
			return false;
		if (startY != other.startY)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (textHeight != other.textHeight)
			return false;
		if (textWidth != other.textWidth)
			return false;
		if (textX != other.textX)
			return false;
		if (textY != other.textY)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (trianglePosition != other.trianglePosition)
			return false;
		if (type != other.type)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LifelineBean [text=");
		builder.append(text);
		builder.append(", startX=");
		builder.append(startX);
		builder.append(", startY=");
		builder.append(startY);
		builder.append(", endX=");
		builder.append(endX);
		builder.append(", endY=");
		builder.append(endY);
		builder.append(", textX=");
		builder.append(textX);
		builder.append(", textY=");
		builder.append(textY);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", textWidth=");
		builder.append(textWidth);
		builder.append(", textHeight=");
		builder.append(textHeight);
		builder.append(", from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", type=");
		builder.append(type);
		builder.append(", trianglePosition=");
		builder.append(trianglePosition);
		builder.append("]");
		return builder.toString();
	}

}
