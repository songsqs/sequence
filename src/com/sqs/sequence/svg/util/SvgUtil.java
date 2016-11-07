package com.sqs.sequence.svg.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
import com.sqs.sequence.svg.bean.Path;
import com.sqs.sequence.svg.bean.Rect;
import com.sqs.sequence.svg.bean.Svg;
import com.sqs.sequence.svg.bean.Text;

public class SvgUtil {

	/**
	 * 根据参数生成svg格式的xml字符串
	 * 
	 * @param objectBeanList
	 * @param lifelineBeanList
	 * @return
	 */
	public static String generateSvgString(List<ObjectBean> objectBeanList, List<LifelineBean> lifelineBeanList) {

		return null;
	}

	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(Svg.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

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
			svg.setRescList(rectList);
			svg.setTextList(textList);

			marshaller.marshal(svg, sw);


			System.out.println(sw.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
