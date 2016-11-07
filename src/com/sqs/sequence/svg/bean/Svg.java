
package com.sqs.sequence.svg.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "svg", namespace = "http://www.w3.org/2000/svg")
public class Svg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5392610868311452181L;

	@XmlAttribute(name = "xmlns")
	private String xmlns;

	@XmlAttribute(name = "xmlns:xlink")
	private String xlink;

	@XmlAttribute(name = "width")
	private String width;

	@XmlAttribute(name = "height")
	private String height;

	@XmlElement(name = "source")
	private String source;

	@XmlElement(name = "rect")
	private List<Rect> rescList;

	@XmlElement(name = "text")
	private List<Text> textList;

	@XmlElement(name = "path")
	private List<Path> pathList;

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getXlink() {
		return xlink;
	}

	public void setXlink(String xlink) {
		this.xlink = xlink;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<Rect> getRescList() {
		return rescList;
	}

	public void setRescList(List<Rect> rescList) {
		this.rescList = rescList;
	}

	public List<Text> getTextList() {
		return textList;
	}

	public void setTextList(List<Text> textList) {
		this.textList = textList;
	}

	public List<Path> getPathList() {
		return pathList;
	}

	public void setPathList(List<Path> pathList) {
		this.pathList = pathList;
	}

}
