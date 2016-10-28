package com.sqs.sequence.server;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
import com.sqs.sequence.enums.PositionEnum;
import com.sqs.sequence.utils.Pair;

public class Parser {

	private final static int OBJECTBEAN_DEFAULT_PADDING_LEFT = 10;
	private final static int OBJECTBEAN_DEFAULT_PADDING_RIGHT = 10;
	private final static int OBJECTBEAN_DEFAULT_PADDING_TOP = 10;
	private final static int OBJECTBEAN_DEFAULT_PADDING_BOTTOM = 10;

	private int objectbean_padding_left = OBJECTBEAN_DEFAULT_PADDING_LEFT;
	private int objectbean_padding_right = OBJECTBEAN_DEFAULT_PADDING_RIGHT;
	private int objectbean_padding_top = OBJECTBEAN_DEFAULT_PADDING_TOP;
	private int objectbean_padding_bottom = OBJECTBEAN_DEFAULT_PADDING_BOTTOM;

	public Parser() {

	}

	public Parser(int objectbeanPadding, int lifelinePadding) {
		this.objectbean_padding_left = objectbeanPadding;
		this.objectbean_padding_right = objectbeanPadding;
		this.objectbean_padding_top = objectbeanPadding;
		this.objectbean_padding_bottom = objectbeanPadding;
	}


	/**
	 * 解析输入字符串
	 * 
	 * @param graphics2d
	 *            java swing 画板,用于确定字符串所需长度
	 * 
	 * @param input
	 *            输入字符串
	 * @return
	 */
	public Pair<List<ObjectBean>, List<LifelineBean>> parse(Graphics2D graphics2d, String input) {

		return this.parse(graphics2d, input, true);
	}

	/**
	 * 解析输入字符串
	 * 
	 * @param graphics2d
	 *            java swing 画板,用于确定字符串所需长度
	 * 
	 * @param input
	 *            输入字符串
	 * @param ignoreError
	 *            忽略语法错误
	 * @return
	 */
	public Pair<List<ObjectBean>, List<LifelineBean>> parse(Graphics2D graphics2d, String input, boolean ignoreError) {
		if (input == null || input.length() == 0) {
			if (ignoreError == false) {
				throw new RuntimeException("input can't be null");
			}
			return null;
		}

		Map<String, ObjectBean> objectBeanMap = new HashMap<>();
		List<ObjectBean> objectBeanList = new ArrayList<>();
		List<LifelineBean> lifelineBeanList = new ArrayList<>();

		parsePhase1(graphics2d, input, objectBeanMap, objectBeanList, lifelineBeanList, ignoreError);

		Pair<List<ObjectBean>, List<LifelineBean>> result = new Pair<>();
		result.setFirst(objectBeanList);
		result.setSecond(lifelineBeanList);
		return result;
	}

	/**
	 * 第一阶段解析
	 * 
	 * @param graphics2d
	 * @param input
	 * @param objectBeanMap
	 * @param lifelineBeanList
	 * @param lifelineBeanlist
	 */
	private void parsePhase1(Graphics2D graphics2d, String input, Map<String, ObjectBean> objectBeanMap,
			List<ObjectBean> objectBeanList,
			List<LifelineBean> lifelineBeanList, boolean ignoreError) {

		FontMetrics fm = graphics2d.getFontMetrics();

		String[] lines = input.split("\n");
		int order = 0;
		for (String lineT : lines) {
			if (lineT.contains(":") == false) {
				if (ignoreError == false) {
					throw new RuntimeException("Bad pattern in:" + lineT);
				}
				continue;
			}
			String[] pair = lineT.split(":");
			if (pair.length != 2) {
				if (ignoreError == false) {
					throw new RuntimeException("Bad pattern in:" + lineT);
				}
				continue;
			}

			LifelineBean lifelineBean = new LifelineBean();
			lifelineBean.setText(pair[1]);
			String[] beanNames;

			if (pair[0].contains("-->")) {
				// lifelineBean.setTrianglePosition(PositionEnum.END);
				beanNames = pair[0].split("-->");
			} else if (pair[0].contains("->")) {
				// lifelineBean.setTrianglePosition(PositionEnum.HEAD);
				beanNames = pair[0].split("->");
			} else {
				if (ignoreError == false) {
					throw new RuntimeException("Bad pattern in:" + lineT);
				}
				continue;
			}

			if (beanNames.length != 2) {
				if (ignoreError == false) {
					throw new RuntimeException("Bad pattern in:" + lineT);
				}
				continue;
			}

			if (beanNames[0].equals(beanNames[1])) {
				if (objectBeanMap.containsKey(beanNames[0]) == false) {
					ObjectBean objectBean = new ObjectBean();
					objectBean.setText(beanNames[0]);
					objectBean.setOrder(order++);
					objectBeanMap.put(beanNames[0], objectBean);
					objectBeanList.add(objectBean);
					initObjectBean(objectBean, fm, graphics2d);
				}
				lifelineBean.setFrom(objectBeanMap.get(beanNames[0]));
				lifelineBean.setTo(objectBeanMap.get(beanNames[0]));
				lifelineBean.setTrianglePosition(PositionEnum.HEAD);
			} else {
				if (objectBeanMap.containsKey(beanNames[0]) == false) {
					ObjectBean objectBean = new ObjectBean();
					objectBean.setText(beanNames[0]);
					objectBean.setOrder(order++);
					objectBeanMap.put(beanNames[0], objectBean);
					objectBeanList.add(objectBean);
					initObjectBean(objectBean, fm, graphics2d);
				}
				if (objectBeanMap.containsKey(beanNames[1]) == false) {
					ObjectBean objectBean = new ObjectBean();
					objectBean.setText(beanNames[1]);
					objectBean.setOrder(order++);
					objectBeanMap.put(beanNames[1], objectBean);
					objectBeanList.add(objectBean);
					initObjectBean(objectBean, fm, graphics2d);
				}
				lifelineBean.setFrom(objectBeanMap.get(beanNames[0]));
				lifelineBean.setTo(objectBeanMap.get(beanNames[1]));
				if (lifelineBean.getFrom().getOrder() < lifelineBean.getTo().getOrder()) {
					lifelineBean.setTrianglePosition(PositionEnum.END);
				} else {
					lifelineBean.setTrianglePosition(PositionEnum.HEAD);
				}
			}
			lifelineBeanList.add(lifelineBean);
		}
	}

	/**
	 * 初始化ObjectBean,设置width和height
	 * 
	 * @param objectBean
	 * @param fm
	 * @param graphics2d
	 */
	private void initObjectBean(ObjectBean objectBean, FontMetrics fm, Graphics2D graphics2d) {
		Rectangle2D rec = fm.getStringBounds(objectBean.getText(), graphics2d);
		int stringWidth = (int) rec.getWidth();
		int stringHeight = (int) rec.getHeight();
		objectBean.setWidth(objectbean_padding_left + stringWidth + objectbean_padding_right);
		objectBean.setHeight(objectbean_padding_top + stringHeight + objectbean_padding_bottom);
	}

	public int getObjectbean_padding_left() {
		return objectbean_padding_left;
	}

	public void setObjectbean_padding_left(int objectbean_padding_left) {
		this.objectbean_padding_left = objectbean_padding_left;
	}

	public int getObjectbean_padding_right() {
		return objectbean_padding_right;
	}

	public void setObjectbean_padding_right(int objectbean_padding_right) {
		this.objectbean_padding_right = objectbean_padding_right;
	}

	public int getObjectbean_padding_top() {
		return objectbean_padding_top;
	}

	public void setObjectbean_padding_top(int objectbean_padding_top) {
		this.objectbean_padding_top = objectbean_padding_top;
	}

	public int getObjectbean_padding_bottom() {
		return objectbean_padding_bottom;
	}

	public void setObjectbean_padding_bottom(int objectbean_padding_bottom) {
		this.objectbean_padding_bottom = objectbean_padding_bottom;
	}

}
