package com.sqs.sequence.server;

import java.awt.Graphics2D;
import java.util.List;

import com.sqs.sequence.bean.LifelineBean;
import com.sqs.sequence.bean.ObjectBean;
import com.sqs.sequence.utils.Pair;

public class Parser {

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
		return null;
	}
}
