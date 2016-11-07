package com.sqs.sequence.svg.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rect")
public class Rect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8199000870469096834L;

	@XmlAttribute(name = "x")
	private String x;

	@XmlAttribute(name = "y")
	private String y;

	@XmlAttribute(name = "width")
	private String width;

	@XmlAttribute(name = "height")
	private String height;

	@XmlAttribute(name = "fill")
	private String fill;

	@XmlAttribute(name = "stroke")
	private String stroke;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

}
