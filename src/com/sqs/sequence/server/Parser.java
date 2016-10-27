package com.sqs.sequence.server;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
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

		return null;
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
		FontMetrics fm = graphics2d.getFontMetrics();

		String[] lines = input.split("\n");
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
			}
		}

		return null;
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
