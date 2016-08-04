package com.qh.app.resource.pay;

import java.util.Random;

/**
 * 公共工具类
 *
 */
public class CommonUtil {
	// 字符0-9, A-Z
	private static final char[] def = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z' };

	/**
	 * 生成随机数
	 * 
	 * @param length 长度
	 * @return String 
	 * @Author tangcheng
	 * @Date 2015年6月19日
	 */
	public static String generateRandomNumber(int length) {
		StringBuffer buffer = new StringBuffer();
		Random ran = new Random();
		for (int i = 0; i < length; i++) {
			int num = ran.nextInt(36);
			char c = def[num];
			buffer.append(c);
		}
		return buffer.toString();
	}
}
