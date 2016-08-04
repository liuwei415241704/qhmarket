package com.qh.app.resource.pay.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

public class CommonUtils {
	/**
	 * 解析XML Document对象，转换成Map
	 * 
	 * @param document
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> parseDocument2Map(Document document) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != document) {
			Element root = document.getRootElement();
			// 从根节点下依次遍历，获取根节点下所有子节点
			Iterator it = root.elementIterator();
			while (it.hasNext()) {// 遍历该子节点
				Object obj = it.next();// 再获取该子节点下的子节点
				Element element = (Element) obj;
				map.put(element.getName(), element.getText());
			}
		}
		return map;
	}

	/**
	 * 参数值的参数按照参数名ASCII码从小到大排序（字典序） 例如：key1=value1&key2=value2
	 * 
	 * @param params
	 * @param ignoreKeys
	 *            忽略的参数
	 * @return String
	 * @date 2015年6月18日
	 */
	public static String joinKeyValueToStr(Map<String, Object> params,
			String... ignoreKeys) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		Object value = "";
		for (String key : keys) {
			if (ArrayUtils.contains(ignoreKeys, key)) {
				continue;
			}
			value = params.get(key);
			if (value == null || StringUtils.isEmpty(value.toString())) {
				continue;
			}
			sb.append(key).append("=").append(value.toString()).append("&");
		}
		sb.deleteCharAt(sb.lastIndexOf("&"));
		return sb.toString();
	}

}
