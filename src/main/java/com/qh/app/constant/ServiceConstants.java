package com.qh.app.constant;


public class ServiceConstants {
	public static final String UPLOAD_FILE_PROGRESS = "upload_ps"; // 文件上传进度

	public static final String UPLOAD_FILE_PATH = "app/user"; // 服务端端保存图片的路径
	
	public static final String UPLOAD_FILE_MARKET_PATH = "app/market"; //服务器保存跳蚤市场图片

	public static final String SUCCESS_CODE = "0"; // 0代表请求成功
	public static final String FALSE_CODE = "-1"; // -1代表请求失败

	public static final String INVALID_URL = "500"; // 无效请求路径
	public static final String INVALID_URL_MSG = "无效路径"; //

	public static final String INVALID_TOKEN_CODE = "40001";
	public static final String INVALID_TOKEN_MSG = "无效token或者token不存在";

	public static final String SUCCESS_MSG = "成功";
	public static final String FALSE_MSG = "失败";

	public static final String APPSMS_SEND_SUCCESS_CODE = "000000"; // 短信发送成功
	public static final String APPSMS_SEND_OUT_CODE = "105122";// 同一手机一天内发送的短信已经超过限制
	public static final String APPSMS_SEND_OUT_CODE_MSG = "一天内发送的短信已经超过限制";
	
	/** -----------------------------------注册信息--------------------- **/
	public static final String APPUSER_REGISTER_FALSE_CODE = "-1"; // 注册失败
	public static final String APPUSER_REGISTER_FALSE_MSG = "注册失败请重试!"; // 信息
	public static final String APPUSER_REGISTER_FALSE_BY_MSGCODE_CODE = "-2"; // 验证码不通过,注册失败
	public static final String APPUSER_REGISTER_FALSE_BY_MSGCODE_MSG = "验证码错误!"; // 信息
	public static final String APPUSER_REGISTER_EXITS_CODE = "-3!"; // 用户已经存在
	public static final String APPUSER_REGISTER_EXITS_MSG = "用户已存在!"; // 信息
	
	/** -----------------------------------忘记密码/修改密码--------------------- **/
	public static final String APPUSER_UPDATE_PWD_FALSE_MSGCODE_CODE = "-2";     // 验证码不通过,修改密码失败
	public static final String APPUSER_UPDATE_PWD_FALSE_MSGCODE_MSG = "验证码错误!"; // 信息
	public static final String APPUSER_UPDATE_PWD_COMMON_CODE = "-1";            // 新密码和原始密码不能相同
	public static final String APPUSER_UPDATE_PWD_COMMON_MSG = "新密码和原始密码不能一致!"; // 信息
	
	/** ----------------------------检查手机号是否已经被注册--------------------- **/
	public static final String MOBILE_EXIST_CODE = "-1"; // 错误代码
	public static final String MOBILE_EXIST_MSG = "手机号已注册"; // 错误信息
	
	/** --------------------------------登录------------------------------ **/
	public static final String APPUSER_NOT_EXITS_OR_PWD_ERROR_MSG = "用户不存在或者密码错误"; // 信息
	public static final String APPUSER_NOT_EXITS_OR_PWD_ERROR_CODE = "-1"; // code
	public static final String APPUSER_PARAM_OR_PWD_PARAM_ERROR_MSG = "用户或者密码不能为空"; // 信息
	public static final String APPUSER_PARAM_OR_PWD_PARAM_ERROR_CODE = "-2"; // code

	/** 支付宝交易 **/
	public static final String APP_ALIPAY_TRADE_FINISHED = "TRADE_FINISHED"; // 支付宝交易完成
	public static final String APP_ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS"; // 支付宝交易成功
	public static final String APP_ALIPAY_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";// 等待交易
	public static final String APP_ALIPAY_TRADE_CLOSED = "TRADE_CLOSED";// 未支付
																		// 交易关闭
	public static final String APP_ALIPAY_TRADE_PENDING = "TRADE_PENDING";// 等待卖家收款（买家付款后，如果卖家账号被冻结）。
	/** 微信交易 **/
	public static final String WX_RETURN_SUCCESS_CODE = "SUCCESS"; // 返回状态
	public static final String WX_RESULT_SUCCESS_CODE = "SUCCESS"; // 结果状态

	/** 货币类型 **/
	public static final String APP_CURRENCY_BY_RMB = "CNY";// 人民币

	/** ---------------------------------------交易信息--------------------- **/
	public static final String APP_PAY_SUCCESS = "success";// 交易成功    success
	public static final String APP_PAY_FAIL = "fail";// 交易失败

	public static final String APP_ORDERS_FINISH = "1"; 			// 订单支付：支付成功
	public static final String APP_ORDERS_CANCEL = "2"; 			// 订单取消
	public static final String APP_ORDERS_CREATE = "3"; 			// 订单生成：等待支付
	public static final String APP_ORDERS_FAILED = "4"; 			// 支付失败

	/** ---------------------------------------支付渠道--------------------- **/
	public static final String APP_PAY_CHANNEL_BY_WXPAY = "weixinpay";// 交易成功
	public static final String APP_PAY_CHANNEL_BY_ALIPAY = "alipay";// 交易失败

	/** ---------------------------------------支付状态-------------------- **/
	public static final String APP_PAY_TRADE_STATUS_PAY_NO = "1";// 未支付
	public static final String APP_PAY_TRADE_STATUS_PAYYING = "2";// 支付进行中
	public static final String APP_PAY_TRADE_STATUS_PAY_SUCCESS = "3";// 支付成功
	public static final String APP_PAY_TRADE_STATUS_PAUY_FAILED = "4";// 支付失败
	
	/** ---------------------------------------商户收藏状态------------------ **/
	public static final String ADD_STOREBUSINESS_ALREADY_EXITS_CODE = "10001";
	public static final String ADD_STOREBUSINESS_ALREADY_EXITS_MSG = "该商户已经收藏，不能重复收藏！";
	
	/** ---------------------------------------附件类别------------------ **/
	public static final String ATTACH_TYPE_BUSINESS = "1";
	
	public static final String ATTACH_TYPE_GOODS = "2";
	
	public static final String ATTACH_TYPE_MARKET = "3";
}
