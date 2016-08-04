package com.qh.app.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.util.JSONCommUtils;
import com.qh.app.util.RedisUtil;
import com.qh.app.util.StringUtil;

/**
 * 前端请求参数统一入口
 * 
 * @author lw
 * 
 */

@Controller
@RequestMapping({ "/common" })
public class CommonAction {
	
	private static final String[] IGNORE_URI = { "/base/appUser", "/sms"};
	
	private static Logger logger = LoggerFactory.getLogger(CommonAction.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/request" }, produces = { "application/json;charset=UTF-8" })
	public ModelAndView parseRequest(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes attr,
			@RequestBody JSONObject reqData) {
		RequestData<Object> requestData = new RequestData<>();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String, String> attributes = new HashMap<String, String>();
		logger.info("****"+reqData + "****");
		reqData = JSONCommUtils.parseRequestReqData(reqData);
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestHead(
				reqData, requestData);
		logger.info("****请求处理开始****");
		String action = requestData.getRequestHead().getAction();
		if (StringUtil.isEmpty(action)) {
			// 非法路径
			attributes.put("msg", ServiceConstants.INVALID_URL_MSG);
			attributes.put("code", ServiceConstants.INVALID_TOKEN_CODE);
			view.setAttributesMap(attributes);
			return new ModelAndView(view);
		}
		String token = requestData.getRequestHead().getToken();
		String userId = requestData.getRequestHead().getUserid();
		Page page = requestData.getRequestHead().getPage();
		boolean need = isNeedToken(action.trim());
		if (need) {
			// 判断token是否正确 缓存中是否存在这个token
			logger.info("userid: {}", userId);
			String _tokenValue = RedisUtil.get(userId);
			if (StringUtil.isEmpty(token) || StringUtil.isEmpty(_tokenValue)
					|| !_tokenValue.equals(token)) {
				// 非法请求
				attributes.put("msg", ServiceConstants.INVALID_TOKEN_MSG);
				attributes.put("code", ServiceConstants.INVALID_TOKEN_CODE);
				view.setAttributesMap(attributes);
				return new ModelAndView(view);
			}
		}
		attr.addFlashAttribute("reqData", reqData);
		attr.addFlashAttribute("userId", userId);
		if (page != null) {
			attr.addFlashAttribute("page", page);
		}
		//请求转发
//		ModelMap modelMap = new ModelMap();
//		modelMap.addAttribute("reqData", reqData);
//		modelMap.addAttribute("userId", userId);
//		if (page != null) {
//			modelMap.addAttribute("page", page);
//		}
		//最后的参数为false代表以post方式提交请求
		return new ModelAndView(new RedirectView("/" + action, true, false,
				false));
		//请求转发，重定向时如果出现并发时请求线程错乱，改用转发
//		return new ModelAndView("forward:" + action, modelMap);
	}

	/**
	 * 解析请求,目前只有/base/appUser,/sms下的接口请求不需要token验证,其他都需要
	 * 
	 * @param reqData
	 * @return true 需要token false不需要token
	 */
	public boolean isNeedToken(String action) {
		// 判断请求的action是否需要token
		for (int i = 0; i < IGNORE_URI.length; i++) {
			// 不需要token的请求
			int isInclude = action.indexOf(IGNORE_URI[i]);
			if (isInclude != -1) {
				return false;
			}
		}
		return true;
	}

}
