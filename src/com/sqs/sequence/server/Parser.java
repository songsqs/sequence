package com.sqs.sequence.server;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
import com.sqs.sequence.enums.LifelineTypeEnum;
import com.sqs.sequence.enums.PositionEnum;
import com.sqs.sequence.utils.Pair;

public class Parser {

	// ObjecBean起始留出的空白padding
	private final static int WHITE_PADDING_LEFT = 10;
	private final static int WHITE_PADDING_TOP = 10;
	// private final static int WHITE_PADDING_RIGHT = 10;
	// private final static int WHITE_PADDING_BOTTOM = 10;

	// ObjectBean的默认padding
	private final static int OBJECTBEAN_DEFAULT_PADDING_LEFT = 10;
	private final static int OBJECTBEAN_DEFAULT_PADDING_RIGHT = 10;
	private final static int OBJECTBEAN_DEFAULT_PADDING_TOP = 20;
	private final static int OBJECTBEAN_DEFAULT_PADDING_BOTTOM = 0;

	// ObjectBean的padding
	private int objectbean_padding_left = OBJECTBEAN_DEFAULT_PADDING_LEFT;
	private int objectbean_padding_right = OBJECTBEAN_DEFAULT_PADDING_RIGHT;
	private int objectbean_padding_top = OBJECTBEAN_DEFAULT_PADDING_TOP;
	private int objectbean_padding_bottom = OBJECTBEAN_DEFAULT_PADDING_BOTTOM;

	// LifelineBean的默认padding
	private final static int LIFELINEBEAN_DEFAULT_PADDING_LEFT = 10;
	private final static int LIFELINEBEAN_DEFAULT_PADDING_RIGHT = 10;
	private final static int LIFELINEBEAN_DEFAULT_PADDING_TOP = 10;
	private final static int LIFELINEBEAN_DEFAULT_PADDING_BOTTOM = 0;
	private final static int LIFELINEBEAN_DEFAULT_WIDTH_WHEN_CIRCLE = 20;

	// LifelineBean的padding
	private int lifelinebean_padding_left = LIFELINEBEAN_DEFAULT_PADDING_LEFT;
	private int lifelinebean_padding_right = LIFELINEBEAN_DEFAULT_PADDING_RIGHT;
	private int lifelinebean_padding_top = LIFELINEBEAN_DEFAULT_PADDING_TOP;
	private int lifelinebean_padding_bottom = LIFELINEBEAN_DEFAULT_PADDING_BOTTOM;
	private int lifelinebean_width_when_circle = LIFELINEBEAN_DEFAULT_WIDTH_WHEN_CIRCLE;

	public Parser() {

	}

	public Parser(int objectbeanPadding, int lifelinePadding) {
		this.objectbean_padding_left = objectbeanPadding;
		this.objectbean_padding_right = objectbeanPadding;
		this.objectbean_padding_top = objectbeanPadding;
		this.objectbean_padding_bottom = objectbeanPadding;

		this.lifelinebean_padding_left = lifelinePadding;
		this.lifelinebean_padding_right = lifelinePadding;
		this.lifelinebean_padding_top = lifelinePadding;
		this.lifelinebean_padding_bottom = lifelinePadding;
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

		parsePhase2(lifelineBeanList, objectBeanList);

		parsePhase3(objectBeanList);

		parsePhase4(lifelineBeanList, objectBeanList);

		Pair<List<ObjectBean>, List<LifelineBean>> result = new Pair<>();
		result.setFirst(objectBeanList);
		result.setSecond(lifelineBeanList);
		return result;
	}

	/**
	 * 第一阶段解析<br>
	 * 初始化ObjectBean,LifelineBean
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
				beanNames = pair[0].split("-->");
				lifelineBean.setType(LifelineTypeEnum.DOTTED_LINE);
			} else if (pair[0].contains("->")) {
				beanNames = pair[0].split("->");
				lifelineBean.setType(LifelineTypeEnum.SOLID_LINE);
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
					this.initObjectBean(objectBean, fm, graphics2d);
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
					this.initObjectBean(objectBean, fm, graphics2d);
				}
				if (objectBeanMap.containsKey(beanNames[1]) == false) {
					ObjectBean objectBean = new ObjectBean();
					objectBean.setText(beanNames[1]);
					objectBean.setOrder(order++);
					objectBeanMap.put(beanNames[1], objectBean);
					objectBeanList.add(objectBean);
					this.initObjectBean(objectBean, fm, graphics2d);
				}
				lifelineBean.setFrom(objectBeanMap.get(beanNames[0]));
				lifelineBean.setTo(objectBeanMap.get(beanNames[1]));
				if (lifelineBean.getFrom().getOrder() < lifelineBean.getTo().getOrder()) {
					lifelineBean.setTrianglePosition(PositionEnum.END);
				} else {
					lifelineBean.setTrianglePosition(PositionEnum.HEAD);
				}
			}
			this.initLifelineBean(lifelineBean, fm, graphics2d);
			lifelineBeanList.add(lifelineBean);
		}
	}

	/**
	 * 初始化ObjectBean<br>
	 * 设置width和height
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

	/**
	 * 初始化LifelineBean<br>
	 * 设置width和height<br>
	 * 设置textHeight
	 * 
	 * @param lifelineBean
	 * @param fm
	 * @param graphics2d
	 */
	private void initLifelineBean(LifelineBean lifelineBean, FontMetrics fm, Graphics2D graphics2d) {
		Rectangle2D rec = fm.getStringBounds(lifelineBean.getText(), graphics2d);
		int stringWidth = (int) rec.getWidth();
		int stringHeight = (int) rec.getHeight();
		if (lifelineBean.getFrom().equals(lifelineBean.getTo())) {
			lifelineBean.setWidth(lifelinebean_width_when_circle + stringWidth);
			lifelineBean.setHeight(lifelinebean_padding_top + stringHeight + lifelinebean_padding_bottom);
		} else {
			lifelineBean.setWidth(lifelinebean_padding_left + stringWidth + lifelinebean_padding_right);
			lifelineBean.setHeight(lifelinebean_padding_top + stringHeight + lifelinebean_padding_bottom);
		}
		lifelineBean.setTextHeight(stringHeight);
	}

	/**
	 * 第二阶段解析<br>
	 * 通过LifelineBean设置ObjectBean的间隔<br>
	 * 同时设置ObjectBean的x,y坐标
	 * 
	 * @param lifelineBeanList
	 * @param objectBeanList
	 */
	private void parsePhase2(List<LifelineBean> lifelineBeanList, List<ObjectBean> objectBeanList) {
		// 用于存储A->B类型的距离
		Map<Pair<ObjectBean, ObjectBean>, Integer> distanceMap = new HashMap<>();
		// 用于存储A->A类型的距离
		Map<ObjectBean, Integer> widthMap = new HashMap<>();

		for (LifelineBean lifelineBeanT : lifelineBeanList) {
			if (lifelineBeanT.getFrom().equals(lifelineBeanT.getTo())) {
				int circleWidth = lifelineBeanT.getWidth();
				if (widthMap.get(lifelineBeanT.getFrom()) == null
						|| widthMap.get(lifelineBeanT.getFrom()).intValue() < circleWidth) {
					widthMap.put(lifelineBeanT.getFrom(), circleWidth);
				}
			} else {
				Pair<ObjectBean, ObjectBean> pair = new Pair<>();
				if (lifelineBeanT.getFrom().getOrder() < lifelineBeanT.getTo().getOrder()) {
					pair.setFirst(lifelineBeanT.getFrom());
					pair.setSecond(lifelineBeanT.getTo());
				} else {
					pair.setFirst(lifelineBeanT.getTo());
					pair.setSecond(lifelineBeanT.getFrom());
				}
				int distince = lifelineBeanT.getWidth() + pair.getFirst().getWidth() / 2
						- pair.getSecond().getWidth() / 2;
				if (distanceMap.get(pair) == null || distanceMap.get(pair).intValue() < distince) {
					distanceMap.put(pair, distince);
				}
			}
		}

		// 计算每个ObjectBean到起始ObjectBean的距离
		Map<ObjectBean, Integer> fromStartDistanceMap = new HashMap<>();
		List<Pair<ObjectBean, ObjectBean>> pairList = new ArrayList<>(distanceMap.keySet());
		Collections.sort(pairList, new ObjectBeanPairComparator());
		ObjectBean startObjectBean = null;
		if (pairList.size() <= 0) {
			return;
		}
		startObjectBean = pairList.get(0).getFirst();
		fromStartDistanceMap.put(startObjectBean, 0);

		for (Pair<ObjectBean, ObjectBean> pairT : pairList) {
			Integer firstDistacneFromStart = fromStartDistanceMap.get(pairT.getFirst());
			int firstDistacneFromStartInt = 0;
			if (firstDistacneFromStart == null) {
				firstDistacneFromStart = 0;
				fromStartDistanceMap.put(pairT.getFirst(), firstDistacneFromStart);
			}
			firstDistacneFromStartInt = fromStartDistanceMap.get(pairT.getFirst()).intValue();

			Integer circleWidth = widthMap.get(pairT.getFirst());
			if (circleWidth == null) {
				circleWidth = 0;
			}
			int circleWidthInt = circleWidth.intValue();
			int distinceToPre = 0;
			if (circleWidthInt > distanceMap.get(pairT).intValue()) {
				distinceToPre = circleWidthInt;
			} else {
				distinceToPre = distanceMap.get(pairT).intValue();
			}

			int secondDistanceFromStartInt = distinceToPre + firstDistacneFromStartInt;
			if (fromStartDistanceMap.get(pairT.getSecond()) == null
					|| fromStartDistanceMap.get(pairT.getSecond()).intValue() < secondDistanceFromStartInt) {
				fromStartDistanceMap.put(pairT.getSecond(), secondDistanceFromStartInt);
			}

		}

		// 计算每个ObjectBean的x坐标,y坐标直接指定
		for (ObjectBean objectbeanT : objectBeanList) {
			Integer fromStartDistance = fromStartDistanceMap.get(objectbeanT);
			if (fromStartDistance == null) {
				fromStartDistance = 0;
			}
			objectbeanT.setX(fromStartDistance.intValue() + WHITE_PADDING_LEFT);
			objectbeanT.setY(WHITE_PADDING_TOP);
			objectbeanT.setTextX(objectbeanT.getX() + objectbean_padding_left);
			objectbeanT.setTextY(objectbeanT.getY() + objectbean_padding_top);
		}

	}

	/**
	 * 第三阶段解析<br>
	 * 设置ObjectBean的轴线x坐标,y坐标
	 * 
	 * @param objectBeanList
	 * @param lifelineBeanList
	 */
	private void parsePhase3(List<ObjectBean> objectBeanList) {
		for (ObjectBean objectbeanT : objectBeanList) {
			objectbeanT.setLineX(objectbeanT.getX() + objectbeanT.getWidth() / 2);
			objectbeanT.setLineY(objectbeanT.getY() + objectbeanT.getHeight());
		}
	}

	/**
	 * 第四阶段解析<br>
	 * 设置LifelineBean的startX,startY,endX,endY,textX,textY<br>
	 * 设置ObjectBean的lineLength
	 * 
	 * @param lifelineBeanList
	 */
	private void parsePhase4(List<LifelineBean> lifelineBeanList, List<ObjectBean> objectBeanList) {
		int height = WHITE_PADDING_TOP;
		if (objectBeanList.size() <= 0) {
			return;
		}
		int objectBeanHeight=objectBeanList.get(0).getHeight()+objectBeanList.get(0).getY();
		height += objectBeanHeight;
		for (LifelineBean lifelinebeanT : lifelineBeanList) {
			if (lifelinebeanT.getFrom().equals(lifelinebeanT.getTo())) {
				lifelinebeanT.setStartY(height);
				lifelinebeanT.setStartX(lifelinebeanT.getFrom().getLineX());
				lifelinebeanT.setTextX(lifelinebean_width_when_circle + lifelinebeanT.getStartX());
				lifelinebeanT.setTextY(lifelinebeanT.getStartY() + lifelinebean_padding_top);
				lifelinebeanT.setEndX(lifelinebeanT.getStartX() + lifelinebean_width_when_circle);
				lifelinebeanT.setEndY(lifelinebeanT.getStartY() + lifelinebeanT.getHeight());

				height += lifelinebeanT.getHeight();
			} else {
				height += lifelinebeanT.getHeight();
				lifelinebeanT.setStartY(height);

				if (lifelinebeanT.getFrom().getOrder() < lifelinebeanT.getTo().getOrder()) {
					lifelinebeanT.setStartX(lifelinebeanT.getFrom().getLineX());
					lifelinebeanT.setEndX(lifelinebeanT.getTo().getLineX());
				} else {
					lifelinebeanT.setStartX(lifelinebeanT.getTo().getLineX());
					lifelinebeanT.setEndX(lifelinebeanT.getFrom().getLineX());
				}
				lifelinebeanT.setEndY(height);
				lifelinebeanT.setTextX(lifelinebeanT.getStartX() + lifelinebean_padding_left);
				lifelinebeanT.setTextY(lifelinebeanT.getStartY() - lifelinebeanT.getTextHeight());
			}

			height += WHITE_PADDING_TOP;
		}

		for (ObjectBean objectBeanT : objectBeanList) {
			objectBeanT.setLineLength(height - objectBeanHeight);
		}

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

	public int getLifelinebean_padding_left() {
		return lifelinebean_padding_left;
	}

	public void setLifelinebean_padding_left(int lifelinebean_padding_left) {
		this.lifelinebean_padding_left = lifelinebean_padding_left;
	}

	public int getLifelinebean_padding_right() {
		return lifelinebean_padding_right;
	}

	public void setLifelinebean_padding_right(int lifelinebean_padding_right) {
		this.lifelinebean_padding_right = lifelinebean_padding_right;
	}

	public int getLifelinebean_padding_top() {
		return lifelinebean_padding_top;
	}

	public void setLifelinebean_padding_top(int lifelinebean_padding_top) {
		this.lifelinebean_padding_top = lifelinebean_padding_top;
	}

	public int getLifelinebean_padding_bottom() {
		return lifelinebean_padding_bottom;
	}

	public void setLifelinebean_padding_bottom(int lifelinebean_padding_bottom) {
		this.lifelinebean_padding_bottom = lifelinebean_padding_bottom;
	}

	public int getLifelinebean_width_when_circle() {
		return lifelinebean_width_when_circle;
	}

	public void setLifelinebean_width_when_circle(int lifelinebean_width_when_circle) {
		this.lifelinebean_width_when_circle = lifelinebean_width_when_circle;
	}

	private class ObjectBeanPairComparator implements Comparator<Pair<ObjectBean, ObjectBean>> {

		@Override
		public int compare(Pair<ObjectBean, ObjectBean> o1, Pair<ObjectBean, ObjectBean> o2) {
			if (o1.getFirst().getOrder() != o2.getFirst().getOrder()) {
				return o1.getFirst().getOrder() - o2.getFirst().getOrder();
			}
			return o1.getSecond().getOrder() - o2.getSecond().getOrder();
		}

	}

}
