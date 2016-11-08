package com.sqs.sequence.svg.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "path")
public class Path implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121319070986334047L;
	
	@XmlAttribute(name = "stroke")
	private String stroke;

	@XmlAttribute(name = "d")
	private String d;

	@XmlAttribute(name = "stroke-dasharray")
	private String strokeDasharray = "0,0";

	@XmlAttribute(name = "fill")
	private String fill = "none";

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getStrokeDasharray() {
		return strokeDasharray;
	}

	public void setStrokeDasharray(String strokeDasharray) {
		this.strokeDasharray = strokeDasharray;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}
}
