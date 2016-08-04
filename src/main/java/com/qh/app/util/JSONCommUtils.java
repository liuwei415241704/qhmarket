package com.qh.app.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.req.RequestHead;
/**
 * http协议JSON数据格式转换类
 * @author lw
 *
 */
public class JSONCommUtils {
	private static Logger logger = LoggerFactory.getLogger(JSONCommUtils.class);

	public static JSONObject parseRequestReqData(JSONObject reqData) {
		new JSONObject();
		JSONObject obj = JSONObject.fromObject(reqData);
		JSONObject targetJson = (JSONObject) obj.get("reqData");
		return targetJson;
	}

	public static <T> Object parseRequestHead(JSONObject reqData,
			RequestData<?> requestData) {
		new JSONObject();
		JSONObject obj = JSONObject.fromObject(reqData);
		String headJson = obj.get("head").toString();
		logger.info("head的内容：" + headJson);

		RequestHead requestHead = (RequestHead) jsonToBean(headJson,
				RequestHead.class);
		requestData.setRequestHead(requestHead);
		return requestData;
	}

	public static <T> Object parseRequestByCollection(JSONArray listJson,
			Class<?> class1, RequestData<Object> requestData) {
		new JSONObject();
		logger.info("listJson:{}", listJson);
		List<?> body = getJavaCollection((Class<?>) class1, listJson);
		requestData.setBody(body);
		return requestData;
	}

	public static <T> Object parseRequestBody(JSONObject reqData,
			Class<?> class1, RequestData<Object> requestData) {
		new JSONObject();
		JSONObject obj = JSONObject.fromObject(reqData);
		String bodyJson = obj.get("body").toString();
		logger.info("body:{}", bodyJson);
		Object body = jsonToBean(bodyJson, (Class<?>) class1);
		requestData.setBody(body);
		return requestData;
	}

	private static <T> List<T> getJavaCollection(T clazz, JSONArray jsons) {
		List objs = null;
		JSONArray jsonArray = jsons;
		if (jsonArray != null) {
			objs = new ArrayList();
			List list = (List) JSONSerializer.toJava(jsonArray);
			for (Iterator localIterator = list.iterator(); localIterator
					.hasNext();) {
				Object o = localIterator.next();
				JSONObject jsonObject = JSONObject.fromObject(o);

				Object obj = jsonToBean(jsonObject.toString(), (Class<?>) clazz);
				objs.add(obj);
			}
		}
		return objs;
	}

	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Object bean = JSONObject.toBean(jsonObject, beanCalss);
		return (T) bean;
	}
}