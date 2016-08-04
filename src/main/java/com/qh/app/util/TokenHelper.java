package com.qh.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Token 工具类
 * 
 */
public class TokenHelper {

	private static final Logger logger = LoggerFactory.getLogger(TokenHelper.class);

	/**
	 * 将token保存在缓存中,默认以token位key
	 * 
	 * @param token
	 */
	public static void setCacheToken(String token) {
		setCacheToken(token, token);
	}

	/**
	 * 将token保存在缓存中
	 * 
	 * @param tokenName
	 * @param token
	 */
	public static void setCacheToken(String tokenName, String token) {
		RedisUtil.set(tokenName, token);
		logger.info("token存放缓存******");
	}

	/**
	 * 验证当前token是否存在
	 * 
	 * @return 验证结果
	 */
	public static boolean validToken(String token) {
		if (StringUtil.isEmpty(token)) {
			return false;
		}
		// 从缓存中获取token进行比较
		String vtoken = RedisUtil.get(token);
		if (token.equals(vtoken)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成APP访问令牌，采用MD5
	 * 
	 * @return APP访问令牌
	 */
	public static String gennerateToken() {
		return generateGUID();
	}

	synchronized private static String generateGUID() {
		// 获取UUID
		String uuid = UUID.randomUUID().toString();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (StringUtil.isNotEmpty(uuid)) {
				md.update(uuid.getBytes());
			}
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 把字节转换为16进制
	 * 
	 * @param data
	 *            待处理数据
	 * @return
	 */
	public static String toHex(final byte[] data) {
		String result = "";
		if (data != null && data.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (byte b : data) {
				sb.append(String.format("%02X", b));
			}
			result = sb.toString();
		}
		return result;
	}
}
