package com.sqs.sequence.utils;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtil {

	public static String generateXml(Object object) {
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter sw = new StringWriter();
			marshaller.marshal(object, sw);
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static <T> T generteObject(InputStream inputStream, Class<T> classz) {
		try {
			JAXBContext context = JAXBContext.newInstance(classz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			@SuppressWarnings("unchecked")
			T temp = (T) unmarshaller.unmarshal(inputStream);
			return temp;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
