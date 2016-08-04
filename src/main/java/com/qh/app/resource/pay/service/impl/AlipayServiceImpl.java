package com.qh.app.resource.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qh.app.entity.AliPayEntity;
import com.qh.app.resource.pay.config.AlipayConfig;
import com.qh.app.resource.pay.service.AlipayService;
import com.qh.app.resource.pay.util.RSA;


@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {
	@Resource
	private AlipayConfig alipayConfig;

	/**
	 * 配置订单支付签名信息
	 * 
	 * @orderId 系统唯一订单号
	 * @subject 订单名称
	 * @body 商品详情
	 * @amount 订单金额
	 */
	public String getSignData(AliPayEntity aliPayEntity) {
		StringBuilder orderInfo = new StringBuilder("partner=" + "\""
				+ alipayConfig.getPartner() + "\"");
		orderInfo
				.append("&seller_id=" + "\"" + alipayConfig.getSeller() + "\"");
		orderInfo.append("&out_trade_no=" + "\"" + aliPayEntity.getOrderid()
				+ "\"");
		orderInfo.append("&subject=" + "\"" + aliPayEntity.getSubject() + "\"");
		orderInfo.append("&body=" + "\"" + aliPayEntity.getBody() + "\"");
		System.out.println(String.valueOf(Double.parseDouble(aliPayEntity
				.getAmount()) / 100));
		orderInfo
				.append("&total_fee="
						+ "\""
						+ String.valueOf(Double.parseDouble(aliPayEntity
								.getAmount()) / 100) + "\"");// 阿里需要的是以元单位的值
		orderInfo.append("&notify_url=" + "\"" + alipayConfig.getPayNotifyUrl()
				+ "\"");

		// 接口名称， 固定值
		orderInfo.append("&service=\"mobile.securitypay.pay\"");
		// 支付类型， 固定值
		orderInfo.append("&payment_type=\"1\"");
		// 参数编码， 固定值
		orderInfo.append("&_input_charset=\"utf-8\"");

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo.append("&it_b_pay=\"30m\"");

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo.append("&return_url=\"m.alipay.com\"");

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo.toString();
	}

	/**
	 * 配置帐户充值签名信息
	 * 
	 * @orderId 系统唯一订单号
	 * @subject 订单名称
	 * @body 商品详情
	 * @amount 订单金额
	 */

	public String getRechargeSignData(AliPayEntity aliPayEntity) {
		StringBuilder orderInfo = new StringBuilder("partner=" + "\""
				+ alipayConfig.getPartner() + "\"");
		orderInfo
				.append("&seller_id=" + "\"" + alipayConfig.getSeller() + "\"");
		orderInfo.append("&out_trade_no=" + "\"" + aliPayEntity.getOrderid()
				+ "\"");
		orderInfo.append("&subject=" + "\"" + aliPayEntity.getSubject() + "\"");
		orderInfo.append("&body=" + "\"" + aliPayEntity.getBody() + "\"");
		orderInfo
				.append("&total_fee=" + "\"" + aliPayEntity.getAmount() + "\"");
		orderInfo.append("&notify_url=" + "\"" + alipayConfig.getPayNotifyUrl()
				+ "\"");

		// 接口名称， 固定值
		orderInfo.append("&service=\"mobile.securitypay.pay\"");
		// 支付类型， 固定值
		orderInfo.append("&payment_type=\"1\"");
		// 参数编码， 固定值
		orderInfo.append("&_input_charset=\"utf-8\"");

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo.append("&it_b_pay=\"30m\"");

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo.append("&return_url=\"m.alipay.com\"");

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo.toString();
	}

	/**
	 * 对订单信息进行签名
	 * 
	 * @param content
	 *            : 待签名订单信息
	 */
	public String sign(String content) {
		return RSA.sign(content, alipayConfig.getRsaPrivate());
	}
}
