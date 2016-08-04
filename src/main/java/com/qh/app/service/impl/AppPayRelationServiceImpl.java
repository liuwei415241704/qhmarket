package com.qh.app.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.AppOrders;
import com.qh.app.entity.AppPayInfo;
import com.qh.app.resource.pay.MD5Encrypt;
import com.qh.app.resource.pay.config.WxpayConfig;
import com.qh.app.resource.pay.util.AlipayNotify;
import com.qh.app.resource.pay.util.CommonUtils;
import com.qh.app.service.AppOrdersService;
import com.qh.app.service.AppPayInfoService;
import com.qh.app.service.AppPayRelationService;


@Service("appPayRelationService")
public class AppPayRelationServiceImpl implements AppPayRelationService {
	
	private Logger logger = Logger.getLogger(AppPayRelationServiceImpl.class);
	
	@Resource
	private WxpayConfig wxpayConfig;

	@Resource
	private AppPayInfoService appPayInfoService;
	
	@Resource
	private AppOrdersService appOrdersService;

	@Override
	public String addWxPayTradeInfoRelation(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		String returnCode = null;
		String returnMsg = null;
		try {

			SAXReader reader = new SAXReader();
			Document document = reader.read(request.getInputStream());

			Map<String, Object> result = CommonUtils
					.parseDocument2Map(document);

			String tempSign = CommonUtils.joinKeyValueToStr(result, "sign");
			tempSign = tempSign + "&key=" + wxpayConfig.getKey();
			String signValue = MD5Encrypt.encryptMD5(tempSign).toUpperCase(); // 根据微信返回参数重新签名,用于验证

			if (ServiceConstants.WX_RESULT_SUCCESS_CODE.equals(result
					.get("result_code"))) {
				AppPayInfo returnPayInfo = new AppPayInfo();

				String out_trade_no = result.get("out_trade_no").toString(); // 订单id
				String bank_type = result.get("bank_type").toString();
				String fee_type = result.get("fee_type").toString();
				String total_fee = result.get("total_fee").toString();
				String transaction_id = result.get("transaction_id").toString();// 微信支付订单号
				returnPayInfo.setAmount(new BigDecimal(total_fee));
				returnPayInfo.setOrdersid(out_trade_no);
				returnPayInfo
						.setChannel(ServiceConstants.APP_PAY_CHANNEL_BY_WXPAY);
				returnPayInfo.setChannelid(transaction_id);
				returnPayInfo.setCurrency(fee_type);
				returnPayInfo.setPayid(bank_type);
				returnPayInfo.setTradestatus(new BigDecimal(
						ServiceConstants.APP_PAY_TRADE_STATUS_PAY_SUCCESS));

				Set<String> keys = result.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					System.out.println(key + "-->" + result.get(key));
				}
				// 根据订单号查询支付信息
				AppPayInfo payInfo = appPayInfoService
						.queryAppPayInfoByOrdersId(out_trade_no);
				if (payInfo == null) {
					// 新增支付信息
					payInfo = returnPayInfo;
					// 添加支付信息
					appPayInfoService.addAppPayInfo(payInfo);
				} else {
					// 更新支付信息
					returnPayInfo.setPayid(payInfo.getPayid());
					appPayInfoService.updateAppPayInfo(returnPayInfo);

				}

				// 更新订单状态
				AppOrders appOrders = new AppOrders();
				appOrders.setOrdersId(out_trade_no);
				if (StringUtils.isNotBlank(signValue)
						&& signValue.equals(result.get("sign"))) {
					appOrders.setStatus(ServiceConstants.APP_ORDERS_FINISH);
					returnCode = "SUCCESS";
					returnMsg = "OK";
				} else {
					appOrders.setStatus(ServiceConstants.APP_ORDERS_CANCEL);
					returnCode = "FAIL";
					returnMsg = "无效签名";
				}
				appOrdersService.updateOrdersState(appOrders);

			}
			// 拼接返回结果
			StringBuffer buffer = new StringBuffer();
			buffer.append("<xml><return_code>").append(returnCode)
					.append("</return_code>");
			buffer.append("<return_msg>").append(returnMsg)
					.append("</return_msg></xml>");

			response.setHeader("Content-type", "text/xml; charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Catch-Control", "no-cache");

			writer = response.getWriter();
			writer.flush();
			writer.print(buffer.toString());
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
		return "";
	}

	@Override
	public String addAliPayTradeInfoRelation(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				logger.info(name + "--" + valueStr);
				params.put(name, valueStr);
			}
			// 验签失败
			if (!AlipayNotify.verify(params)) {
				logger.info("验签失败！");
				return ServiceConstants.APP_PAY_FAIL;
			}
			String app_order_status = "";
			AppPayInfo returnPayInfo = new AppPayInfo();
			// 商户订单号
			String out_trade_no = new String(params.get("out_trade_no"));
			// 支付宝交易号
			String trade_no = new String(params.get("trade_no"));
			// 交易状态
			String trade_status = new String(params.get("trade_status"));
			// 总交易金额
			String total_fee = new String(params.get("total_fee"));
			// 买家id
			String buyer_id = new String(params.get("buyer_id"));

			logger.info("支付宝返回回来的信息:out_trade_no=" + out_trade_no
					+ ",trade_no=" + trade_no + ",trade_status=" + trade_status);
			// 支付状态
			if (trade_status.equals(ServiceConstants.APP_ALIPAY_WAIT_BUYER_PAY)) {
				logger.info("等待支付！");
				app_order_status = ServiceConstants.APP_ORDERS_FAILED;
				returnPayInfo.setTradestatus(new BigDecimal(
						ServiceConstants.APP_PAY_TRADE_STATUS_PAYYING));
				return ServiceConstants.APP_PAY_FAIL;
			}
			// 支付状态
			if (trade_status.equals(ServiceConstants.APP_ALIPAY_TRADE_CLOSED)) {
				logger.info("订单被关闭！");
				app_order_status = ServiceConstants.APP_ORDERS_FAILED;
				returnPayInfo.setTradestatus(new BigDecimal(
						ServiceConstants.APP_PAY_TRADE_STATUS_PAY_NO));
				return ServiceConstants.APP_PAY_FAIL;
			}
			if (trade_status.equals(ServiceConstants.APP_ALIPAY_TRADE_PENDING)) {
				logger.info("等待卖家收款");
				app_order_status = ServiceConstants.APP_ORDERS_FINISH;
				returnPayInfo.setTradestatus(new BigDecimal(
						ServiceConstants.APP_PAY_TRADE_STATUS_PAYYING));
				return ServiceConstants.APP_PAY_FAIL;

			}

			// 支付状态
			if (trade_status.equals(ServiceConstants.APP_ALIPAY_TRADE_FINISHED)
					|| trade_status
							.equals(ServiceConstants.APP_ALIPAY_TRADE_SUCCESS)) {
				logger.info("支付支付成功！");
				app_order_status = ServiceConstants.APP_ORDERS_FINISH;
				returnPayInfo.setTradestatus(new BigDecimal(
						ServiceConstants.APP_PAY_TRADE_STATUS_PAY_SUCCESS));
			}
			returnPayInfo.setAmount(new BigDecimal(total_fee));
			returnPayInfo.setOrdersid(out_trade_no);
			returnPayInfo
					.setChannel(ServiceConstants.APP_PAY_CHANNEL_BY_ALIPAY);
			returnPayInfo.setChannelid(trade_no);
			returnPayInfo.setCurrency(ServiceConstants.APP_CURRENCY_BY_RMB);
			returnPayInfo.setPayid(buyer_id);

			// 根据订单号查询支付信息

			AppPayInfo payInfo = appPayInfoService
					.queryAppPayInfoByOrdersId(out_trade_no);
			if (payInfo == null) {
				// 新增支付信息
				payInfo = returnPayInfo;
				// 添加支付信息
				appPayInfoService.addAppPayInfo(payInfo);
			} else {
				returnPayInfo.setPayid(payInfo.getPayid());
				appPayInfoService.updateAppPayInfo(returnPayInfo);
			}

			// 更新订单状态

			AppOrders appOrders = new AppOrders();
			appOrders.setOrdersId(out_trade_no);
			appOrders.setStatus(app_order_status);
			appOrdersService.updateOrdersState(appOrders);

			return ServiceConstants.APP_PAY_SUCCESS;
		} catch (Exception e) {
			logger.info("支付宝支付异常:"+e.getMessage());
			return ServiceConstants.APP_PAY_FAIL;
		}
	}

}
