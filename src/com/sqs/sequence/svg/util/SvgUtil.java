package com.sqs.sequence.svg.util;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
import com.sqs.sequence.enums.LifelineTypeEnum;
import com.sqs.sequence.svg.bean.Path;
import com.sqs.sequence.svg.bean.Rect;
import com.sqs.sequence.svg.bean.Svg;
import com.sqs.sequence.svg.bean.Text;
import com.sqs.sequence.utils.XmlUtil;

public class SvgUtil {

	/**
	 * 根据参数生成svg格式的xml字符串
	 * 
	 * @param objectBeanList
	 * @param lifelineBeanList
	 * @return
	 */
	public static String generateSvgString(List<ObjectBean> objectBeanList, List<LifelineBean> lifelineBeanList,
			String source) {
		Svg svg = generateSvgObject(objectBeanList, lifelineBeanList, source);

		return "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>" + XmlUtil.generateXml(svg);
	}

	public static Svg generateSvgObject(List<ObjectBean> objectBeanList, List<LifelineBean> lifelineBeanList,
			String source) {
		int width = 0, height = 0;

		List<Path> pathList = new ArrayList<>();
		List<Text> textList = new ArrayList<>();
		List<Rect> rectList = new ArrayList<>();

		for (ObjectBean objectBeanT : objectBeanList) {
			// 设置width height
			int beanWidth = objectBeanT.getX() + objectBeanT.getWidth();
			if (beanWidth > width) {
				width = beanWidth;
			}

			int beanHeight = objectBeanT.getHeight() + objectBeanT.getHeight() + objectBeanT.getLineLength();
			if (beanHeight > height) {
				height = beanHeight;
			}

			// 生成ObjectBean对应的信息
			// begin Rect
			Rect beginRect = new Rect();
			beginRect.setX(String.valueOf(objectBeanT.getX()));
			beginRect.setY(String.valueOf(objectBeanT.getY()));
			beginRect.setHeight(String.valueOf(objectBeanT.getHeight()));
			beginRect.setWidth(String.valueOf(objectBeanT.getWidth()));
			beginRect.setFill("none");
			beginRect.setStroke("#000000");
			rectList.add(beginRect);

			// end Rect
			Rect endRect = new Rect();
			endRect.setX(String.valueOf(objectBeanT.getX()));
			endRect.setY(String.valueOf(objectBeanT.getY() + objectBeanT.getHeight() + objectBeanT.getLineLength()));
			endRect.setHeight(String.valueOf(objectBeanT.getHeight()));
			endRect.setWidth(String.valueOf(objectBeanT.getWidth()));
			endRect.setFill("none");
			endRect.setStroke("#000000");
			rectList.add(endRect);

			// begin Text
			Text beginText = new Text();
			beginText.setX(String.valueOf(objectBeanT.getTextX()));
			beginText.setY(String.valueOf(objectBeanT.getTextY()));
			beginText.setValue(objectBeanT.getText());
			textList.add(beginText);

			// end Text
			Text endText = new Text();
			endText.setX(String.valueOf(objectBeanT.getTextX()));
			endText.setY(
					String.valueOf(objectBeanT.getTextY() + objectBeanT.getHeight() + objectBeanT.getLineLength()));
			endText.setValue(objectBeanT.getText());
			textList.add(endText);

			// Path
			Path path = new Path();
			path.setStroke("#000000");
			path.setD("M" + objectBeanT.getLineX() + "," + objectBeanT.getLineY() + "L" + objectBeanT.getLineX() + ","
					+ (objectBeanT.getLineY() + objectBeanT.getLineLength()));
			pathList.add(path);
		}

		for (LifelineBean lifelineBeanT : lifelineBeanList) {
			// Text
			Text text = new Text();
			text.setX(String.valueOf(lifelineBeanT.getTextX()));
			text.setY(String.valueOf(lifelineBeanT.getTextY()));
			text.setValue(lifelineBeanT.getText());
			textList.add(text);

			// Path
			pathList.add(generateLifelinePath(lifelineBeanT));

			// Triangle
			pathList.add(generateLifelineTriangle(lifelineBeanT));
		}

		Svg svg = new Svg();
		svg.setWidth(String.valueOf(width * 1.1));
		svg.setHeight(String.valueOf(height * 1.1));
		svg.setSource(source);
		svg.setTextList(textList);
		svg.setPathList(pathList);
		svg.setRectList(rectList);

		return svg;
	}

	public static Path generateLifelinePath(LifelineBean lifelineBean) {
		Path path = new Path();

		if (lifelineBean.getFrom().equals(lifelineBean.getTo())) {
			path.setD("M" + lifelineBean.getStartX() + "," + lifelineBean.getStartY() + "L" + lifelineBean.getEndX()
					+ "," + lifelineBean.getStartY() + "L" + lifelineBean.getEndX() + "," + lifelineBean.getEndY() + "L"
					+ lifelineBean.getStartX() + "," + lifelineBean.getEndY());
		} else {
			path.setD("M" + lifelineBean.getStartX() + "," + lifelineBean.getStartY() + "L" + lifelineBean.getEndX()
					+ "," + lifelineBean.getEndY());
		}

		path.setStroke("#000000");
		if (lifelineBean.getType().equals(LifelineTypeEnum.DOTTED_LINE)) {
			path.setStrokeDasharray("5,5");
		}

		return path;
	}

	public static Path generateLifelineTriangle(LifelineBean lifelineBean) {
		Path path = new Path();

		if (lifelineBean.getFrom().equals(lifelineBean.getTo())) {
			path.setD("M" + lifelineBean.getStartX() + "," + lifelineBean.getEndY() + "L"
					+ (lifelineBean.getStartX() + 5) + "," + (lifelineBean.getEndY() - 5) + "L"
					+ (lifelineBean.getStartX() + 5) + "," + (lifelineBean.getEndY() + 5) + "Z");
		} else if (lifelineBean.getFrom().getOrder() < lifelineBean.getTo().getOrder()) {
			path.setD("M" + lifelineBean.getEndX() + "," + lifelineBean.getEndY() + "L" + (lifelineBean.getEndX() - 5)
					+ "," + (lifelineBean.getEndY() - 5) + "L" + (lifelineBean.getEndX() - 5) + ","
					+ (lifelineBean.getEndY() + 5) + "Z");
		} else {
			path.setD("M" + lifelineBean.getStartX() + "," + lifelineBean.getStartY() + "L"
					+ (lifelineBean.getStartX() + 5) + "," + (lifelineBean.getStartY() - 5) + "L"
					+ (lifelineBean.getStartX() + 5) + "," + (lifelineBean.getStartY() + 5) + "Z");
		}

		path.setFill("black");

		return path;
	}

	public static String getSourceTextFromSvg(InputStream inputStream) {
		Svg svg = XmlUtil.generteObject(inputStream, Svg.class);
		if (svg != null) {
			return svg.getSource();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(Svg.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			StringWriter sw = new StringWriter();

			Text text = new Text();
			text.setX("10");
			text.setY("10");
			text.setValue("desc");
			List<Text> textList = new ArrayList<>();
			textList.add(text);

			Rect rect = new Rect();
			rect.setFill("none");
			rect.setHeight("100");
			rect.setWidth("100");
			rect.setX("10");
			rect.setY("10");
			rect.setStroke("#000000");
			List<Rect> rectList = new ArrayList<>();
			rectList.add(rect);

			Path path = new Path();
			path.setD("M10,10L10,20");
			path.setStroke("#000000");
			List<Path> pathList = new ArrayList<>();
			pathList.add(path);

			Svg svg = new Svg();
			svg.setHeight("1000");
			svg.setWidth("1000");
			svg.setSource("[][][][]");
			svg.setPathList(pathList);
			svg.setRectList(rectList);
			svg.setTextList(textList);

			marshaller.marshal(svg, sw);

			System.out.println(sw.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
