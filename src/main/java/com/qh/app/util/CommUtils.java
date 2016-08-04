package com.qh.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author lw
 *
 * 项目通用工具类
 */
public class CommUtils {
	private static Logger logger = LoggerFactory.getLogger(CommUtils.class);
	
	/**
	 * 手机号码格式验证
	 * @param moblies
	 * @return
	 */
	public static boolean isMobileNO(String moblies) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(moblies);
		logger.info("验证手机号码!");
		return m.matches();
	}
}