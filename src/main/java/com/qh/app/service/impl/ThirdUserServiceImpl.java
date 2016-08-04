package com.qh.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.constant.ClientConstants;
import com.qh.app.constant.RedisServiceConstants;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.dao.AppUserDao;
import com.qh.app.entity.AppUser;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.ThirdUserService;
import com.qh.app.util.RedisUtil;
import com.qh.app.util.TokenHelper;

@Service("thirdUserService")
public class ThirdUserServiceImpl implements ThirdUserService {

	private static Logger logger = LoggerFactory
			.getLogger(ThirdUserServiceImpl.class);

	@Resource
	private AppUserDao appUserDao;

	@Override
	public ResponseService addAppUser(AppUser user) {
		ResponseService result = new ResponseService();
		Map<String, Object> map = new HashMap<String, Object>();

		// 获取主键
		user.setType(ClientConstants.LOGIN_THIRD_TYPE);
		int isSuccess = appUserDao.addAppUser(user);
		// 注册失败
		if (!(isSuccess > 0)) {
			logger.info("注册失败");
			// 返回的信息
			result.setMsg(ServiceConstants.APPUSER_REGISTER_FALSE_MSG);
			result.setCode(ServiceConstants.APPUSER_REGISTER_FALSE_CODE);
			return result;
		}
		// 注册成功,返回token
		result.setSuccess();
		String token = TokenHelper.gennerateToken();
		map.put("userid", user.getCustomerId());
		map.put("token", token);
		// 将token放到缓存里
		RedisUtil.setex(user.getCustomerId(), token, RedisServiceConstants.TOKEN_EXP);
		result.setData(map);
		return result;
	}
}
