package com.qh.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期操作工具类
 * 
 * @since 1.0
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(DateUtil.class);

	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");

	private static final SimpleDateFormat dateFormatYMDHMS = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 格式化日期与时间
	 */
	public static String formatDatetime(long timestamp) {
		return datetimeFormat.format(new Date(timestamp));
	}

	/**
	 * 格式化日期
	 */
	public static String formatDate(long timestamp) {
		return dateFormat.format(new Date(timestamp));
	}

	/**
	 * 格式化时间
	 */
	public static String formatTime(long timestamp) {
		return timeFormat.format(new Date(timestamp));
	}

	/**
	 * 获取当前日期与时间
	 */
	public static String getCurrentDatetime() {
		return datetimeFormat.format(new Date());
	}

	/**
	 * 获取当前日期
	 */
	public static String getCurrentDate() {
		return dateFormat.format(new Date());
	}

	/**
	 * 获取当前时间
	 */
	public static String getCurrentTime() {
		return timeFormat.format(new Date());
	}

	/**
	 * 解析年月日时分秒
	 */
	public static Date parseYMDHMS(String str) {
		Date date = null;
		try {
			date = dateFormatYMDHMS.parse(str);
		} catch (ParseException e) {
			logger.error("解析日期字符串出错！格式：yyyyMMddHHmmss", e);
		}
		return date;
	}

	/**
	 * 解析日期与时间
	 */
	public static Date parseDatetime(String str) {
		Date date = null;
		try {
			date = datetimeFormat.parse(str);
		} catch (ParseException e) {
			logger.error("解析日期字符串出错！格式：yyyy-MM-dd HH:mm:ss", e);
		}
		return date;
	}

	/**
	 * 解析日期
	 */
	public static Date parseDate(String str) {
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			logger.error("解析日期字符串出错！格式：yyyy-MM-dd", e);
		}
		return date;
	}

	/**
	 * 解析时间
	 */
	public static Date parseTime(String str) {
		Date date = null;
		try {
			date = timeFormat.parse(str);
		} catch (ParseException e) {
			logger.error("解析日期字符串出错！格式：HH:mm:ss", e);
		}
		return date;
	}

	/**
	 * 获取当天开始时间，包含年、月、日、时、分、秒
	 */
	public static String getBeginDateOfToday() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);

		return year + "-" + month + "-" + day + " 00:00:00";
	}

	/**
	 * 获取当天结束的时间
	 */
	public static String getEndDateOfToday() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day + " 23:59:59";
	}
	
	/**
	 * 获取当天结束的时间
	 */
	public static Date getAfterOneYearTime() {
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
		Date date=curr.getTime();
		return date;
	}


}
