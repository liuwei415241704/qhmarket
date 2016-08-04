package com.qh.app.constant;

/**
 * redis 常用配置常量,后期优化有需要可以改成读取配置文件
 * 
 * @author lw
 * 
 */
public class RedisServiceConstants {

	public static final Integer TOKEN_EXP = 604800; // token在redis的保存时间 默认一周

	public static final Integer MSG_EXP = 300; // 短信保存时间5分钟

}
