package com.qh.app.resource.pay.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.WxpayEntity;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.resource.pay.MD5Encrypt;
import com.qh.app.resource.pay.config.WxpayConfig;
import com.qh.app.resource.pay.service.WxpayService;
import com.qh.app.resource.pay.util.CommonUtils;



@Service("wxpayService")
public class WxpayServiceImpl implements WxpayService {
	
	private Logger logger = Logger.getLogger(WxpayServiceImpl.class);
	
	@Resource
	private  WxpayConfig wxpayConfig;

	/**
	 * 获取参数列表
	 * 
	 * @param orderId
	 * @param subject
	 * @param body
	 * @param amount
	 * @param recordId
	 * @param notifyUrl
	 * @return
	 */
	public  Map<String, Object> getParameterMap(String orderId,
			String body, String detail, String amount, String ip,
			String nonceStr, String notifyUrl) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("appid", wxpayConfig.getAppid()); // 公众账号ID
		parameterMap.put("mch_id", wxpayConfig.getMchid()); // 商户号
		parameterMap.put("nonce_str", nonceStr);// 随机字符串
		parameterMap.put("body", body);// 订单描述
		parameterMap.put("detail", detail);// 订单详细描述
		parameterMap.put("out_trade_no", orderId);// 商户订单号
		parameterMap.put("total_fee", amount);// 订单金额,单位：分
		parameterMap.put("spbill_create_ip", ip);// 终端IP
		parameterMap.put("notify_url", notifyUrl); // 通知地址
		parameterMap.put("trade_type", "APP"); // 交易类型
		return parameterMap;
	}

	/**
	 * 参数值的参数按照参数名ASCII码从小到大排序（字典序） 拼接成XML格式
	 * <xml><key1>value1</key1><key2>value2</key2></xml>
	 * 
	 * @param params
	 * @param sign
	 *            参数签名，在最后面拼接
	 * @return
	 */
	public static String joinKeyValueToXML(Map<String, Object> params,
			String sign) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder("<xml>");
		Object value = "";
		for (String key : keys) {
			value = params.get(key);
			if (value == null || StringUtils.isEmpty(value.toString())) {
				continue;
			}
			sb.append("<" + key + ">").append(value.toString())
					.append("</" + key + ">");
		}
		// 在最后加上sign
		sb.append("<sign>").append(sign).append("</sign>");
		sb.append("</xml>");
		return sb.toString();
	}

	public  Map<String, String> parseDocumentToStrMap(Document document) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != document) {
			Element root = document.getRootElement();
			// 从根节点下依次遍历，获取根节点下所有子节点
			Iterator<?> it = root.elementIterator();
			while (it.hasNext()) {// 遍历该子节点
				Object obj = it.next();// 再获取该子节点下的子节点
				Element element = (Element) obj;
				map.put(element.getName(), element.getText());
			}
		}
		return map;
	}

	/**
	 * 配置订单支付参数
	 */
	@Override
	public String getPostParams(WxpayEntity aliPayEntity) {
		if (aliPayEntity != null) {
			Map<String, Object> parameterMap = getParameterMap(
					aliPayEntity.getOut_trade_no(), aliPayEntity.getBody(),
					aliPayEntity.getDetail(), aliPayEntity.getTotal_fee(),
					aliPayEntity.getSpbill_create_ip(), aliPayEntity.getNonce_str(),
					wxpayConfig.getPayNotifyUrl());
			// 对签名参数排序
			String str = CommonUtils.joinKeyValueToStr(parameterMap);
			System.out.println("str:  " + str);
			str = str + "&key=" + wxpayConfig.getKey();
			// 进行MD5加密
			String signValue = MD5Encrypt.encryptMD5(str).toUpperCase();
			System.out.println("第一次加密：" + signValue);
			// 拼接XML格式的请求参数
			String xmlParams = joinKeyValueToXML(parameterMap, signValue);
			System.out.println(xmlParams+"************************");
			return xmlParams;
		}
		return null;
	}

	@Override
	public ResponseService parsePreOrderInfo(String responseStr, String nonceStr) {
		ResponseService result = new ResponseService();
		try {
			// 解析预支付信息
			Document document = DocumentHelper.parseText(responseStr);

			Map<String, Object> preMap = CommonUtils.parseDocument2Map(document);
			String return_code = preMap.get("return_code").toString();
			// 支付参数临时对象
			Map<String, Object> tempMap = new HashMap<String, Object>();
			// SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
			if (ServiceConstants.WX_RETURN_SUCCESS_CODE.equals(return_code)) {
				String resultCode = preMap.get("result_code").toString();
				if (ServiceConstants.WX_RESULT_SUCCESS_CODE.equals(resultCode)) {
					// 对预支付信息进行签名、验证
					String preTempSign = CommonUtils.joinKeyValueToStr(preMap, "sign");
					preTempSign = preTempSign + "&key=" + wxpayConfig.getKey();
					String preSignValue = MD5Encrypt.encryptMD5(preTempSign)
							.toUpperCase();
					// 判断签名信息
					if (StringUtils.isNotBlank(preSignValue)
							&& preSignValue.equals(preMap.get("sign"))) {
						String prepay_id = preMap.get("prepay_id").toString();
						long timestamp = System.currentTimeMillis() / 1000;

						// begin===== 对以下字段进行签名=======
						tempMap.put("appid", wxpayConfig.getAppid());
						tempMap.put("partnerid", wxpayConfig.getMchid());
						tempMap.put("prepayid", prepay_id);
						tempMap.put("package", "Sign=WXPay");
						tempMap.put("noncestr", nonceStr);
						tempMap.put("timestamp", timestamp);
						// end===== 对以下字段进行签名=======

						String tempSignStr = CommonUtils.joinKeyValueToStr(tempMap);
						tempSignStr = tempSignStr + "&key="
								+ wxpayConfig.getKey();
						logger.debug("tempSignStr==" + tempSignStr);
						// 进行MD5加密
						String signValue = MD5Encrypt.encryptMD5(tempSignStr)
								.toUpperCase();
						logger.info("signValue==" + signValue);
						tempMap.put("sign", signValue);
						result.setData(tempMap);
						result.setSuccess();
						logger.info("签名返回成功!");
					}
				} else {
					// 出现异常，返回错误信息
					logger.info("签名失败!");
					result.setCode(preMap.get("err_code").toString());
					result.setMsg(preMap.get("err_code_des").toString());
				}
			}

		} catch (DocumentException e) {
			logger.info("签名失败!");
			logger.error(e);
		}
		return result;

	}


}
