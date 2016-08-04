package com.qh.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.constant.RedisServiceConstants;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.dao.AppUserDao;
import com.qh.app.entity.AppUser;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.resource.file.entity.FTPConfig;
import com.qh.app.resource.file.util.FTPUtils;
import com.qh.app.service.AppUserService;
import com.qh.app.util.GenerateUUID;
import com.qh.app.util.RedisUtil;
import com.qh.app.util.SHAUtil;
import com.qh.app.util.StringUtil;
import com.qh.app.util.TokenHelper;


@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {
	
	private static Logger logger = LoggerFactory
			.getLogger(AppUserServiceImpl.class);
	
	@Resource
	private AppUserDao appUserDao;
	
	@Resource
	private FTPConfig ftpConfig;

	@Override
	public AppUser queryAppUserByMobile(String mobile) {
		return appUserDao.queryAppUserByMobile(mobile);
	}
	
	@Override
	public AppUser queryAppUserByOpenId(String openId) {
		return appUserDao.queryAppUserByOpenId(openId);
	}
	
	@Override
	public ResponseService addAppUser(AppUser user) {
		ResponseService result = new ResponseService();
		// 首先判断短信验证码是否正确
		boolean isCorrect = checkMsgCode(user);
		if (!isCorrect) {
			result.setMsg(ServiceConstants.APPUSER_REGISTER_FALSE_BY_MSGCODE_MSG);
			result.setCode(ServiceConstants.APPUSER_REGISTER_FALSE_BY_MSGCODE_CODE);
			logger.info("****注册失败****");
			return result;
		}
		AppUser isExitUser = queryAppUserByMobile(user.getMobile());
		if (isExitUser != null) {
			logger.info("****用户已存在****");
			result.setCode(ServiceConstants.APPUSER_REGISTER_EXITS_CODE);
			result.setMsg(ServiceConstants.APPUSER_REGISTER_EXITS_MSG);
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("注册用户:" + user.getMobile());
		// 加密密码
		user.setPassword(SHAUtil.encrypt(user.getPassword()));
		// 注册用户
		int isSuccess = appUserDao.addAppUser(user);
		// 注册失败
		if (!(isSuccess > 0)) {
			// 返回的信息
			result.setMsg(ServiceConstants.APPUSER_REGISTER_FALSE_MSG);
			result.setCode(ServiceConstants.APPUSER_REGISTER_FALSE_CODE);
			logger.info("****注册失败****");
			return result;
		}
		// 注册成功,返回token
		result.setSuccess();
		String token = TokenHelper.gennerateToken();
		map.put("userid", user.getCustomerId());
		map.put("token", token);
		logger.info("缓存key设置:", user.getCustomerId());
		// 将token放到缓存里
		RedisUtil.setex(user.getCustomerId(), token,
				RedisServiceConstants.TOKEN_EXP);
		result.setData(map);
		logger.info("****注册成功****");
		return result;
	}
	
	@Override
	public ResponseService addThirdAppUser(AppUser user) {
		ResponseService result = new ResponseService();
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取主键
		String userId = GenerateUUID.getShortUuid();
		user.setCustomerId(userId);
		int isSuccess = appUserDao.addAppUser(user);
		// 注册失败
		if (!(isSuccess > 0)) {
			logger.info("****注册失败****");
			// 返回的信息
			result.setMsg(ServiceConstants.APPUSER_REGISTER_FALSE_MSG);
			result.setCode(ServiceConstants.APPUSER_REGISTER_FALSE_CODE);
			return result;
		}
		// 注册成功,返回token
		result.setSuccess();
		String token = TokenHelper.gennerateToken();
		map.put("userid", userId);
		map.put("token", token);
		// 将token放到缓存里
		RedisUtil.setex(userId, token,RedisServiceConstants.TOKEN_EXP);
		result.setData(map);
		return result;
	}
	
	@Override
	public ResponseService queryAppUserByLogin(AppUser user) {
		ResponseService result = new ResponseService();
		String mobile = user.getMobile();
		String password = user.getPassword();
		if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(password)) {
			result.setMsg(ServiceConstants.APPUSER_PARAM_OR_PWD_PARAM_ERROR_MSG);
			result.setCode(ServiceConstants.APPUSER_PARAM_OR_PWD_PARAM_ERROR_CODE);
			return result;
		}
		// 判断登录用户是否正确
		user.setPassword(SHAUtil.encrypt(password));
		AppUser appUser = appUserDao.queryAppUser(user);
		if (appUser == null) {
			result.setMsg(ServiceConstants.APPUSER_NOT_EXITS_OR_PWD_ERROR_MSG);
			result.setCode(ServiceConstants.APPUSER_NOT_EXITS_OR_PWD_ERROR_CODE);
			return result;
		}
		result.setSuccess();
		// 登录用户正确,返回token
		String token = TokenHelper.gennerateToken();
		Map<Object, Object> map = new HashMap<>();
		map.put("userid", appUser.getCustomerId());
		map.put("token", token);
		// 将token放到缓存里
		RedisUtil.setex(appUser.getCustomerId(), token,
				RedisServiceConstants.TOKEN_EXP);
		result.setData(map);
		return result;
	}

	
	/**
	 * 检查短信验证码是否正确
	 * 
	 * @param user
	 */
	private boolean checkMsgCode(AppUser user) {
		// 从缓存里获取短信验证码,以用户的手机作为key
		String msgCode = RedisUtil.get(user.getMobile());
		// 验证码已被清空或者不存在
		if ("".equals(msgCode) || msgCode == null) {
			logger.info("****验证码不存在****");
			return false;
		}
		String userMsgCode = user.getSmsCode();
		if (StringUtil.isEmpty(userMsgCode)) {
			return false;
		}
		if (userMsgCode.equals(msgCode)) {
			// 验证完,将短信从缓存里移除
			RedisUtil.del(user.getMobile());
			return true;
		}
		return false;
	}

	@Override
	public ResponseService updateAppUser(AppUser user) {
		ResponseService result = new ResponseService();
		int isSuccess = appUserDao.updateAppUser(user);
		if (isSuccess > 0) {
			result.setSuccess();
			logger.info("更新用户资料成功!");
		} else {
			logger.info("更新用户资料失败!");
		}
		return result;
	}

	@Override
	public ResponseService queryAppUserById(AppUser user) {
		ResponseService result = new ResponseService();
		if (user != null) {
			// 获取用户资料
			user = appUserDao.queryAppUserById(user.getCustomerId());
			result.setSuccess();
			result.setData(user);
		}
		return result;
	}

	@Override
	public ResponseService updatePortrait(AppUser appUser) {
		ResponseService result = new ResponseService();
		if (appUser != null) {
			// 先获取之前服务器的图片,删除掉
			AppUser user = appUserDao.queryAppUserById(appUser.getCustomerId());
			if (user != null) {
				String url = user.getPortrait();
				if (StringUtil.isNotEmpty(url)) {
					// 生成服务器路径
					String fileName = StringUtils.substring(url,
							StringUtils.lastIndexOf(url, "/") + 1);
					logger.info("用户头像名称:{}", fileName);
					String path = ServiceConstants.UPLOAD_FILE_PATH + "/"
							+ user.getCustomerId();
					FTPUtils ftpUtils = FTPUtils.getInstance(ftpConfig);
					boolean checkFile = ftpUtils.checkFile(path, fileName);
					logger.info("用户头像地址:{}", path);
					if (checkFile) {
						ftpUtils.deleteFile(path, fileName);
					}
					ftpUtils.logout();
				}
				// 更新图片
				result = updateAppUser(appUser);
			}
		}
		return result;

	}

	@Override
	public ResponseService updateAppUserPwd(AppUser user) {
		ResponseService result = new ResponseService();
		// 首先判断短信验证码是否正确
		boolean isCorrect = checkMsgCode(user);
		if (!isCorrect) {
			result.setMsg(ServiceConstants.APPUSER_UPDATE_PWD_FALSE_MSGCODE_MSG);
			result.setCode(ServiceConstants.APPUSER_UPDATE_PWD_FALSE_MSGCODE_CODE);
			logger.info("****短信不匹配修改密码失败****");
			return result;
		}
		if (user != null) {
			// 获取用户之前的密码
			AppUser appUser = appUserDao.queryAppUserByMobile(user.getMobile());
			String oldPwd = appUser.getPassword();
			String newPwd = SHAUtil.encrypt(user.getPassword());
			// 判断当前设置的密码和新密码是否一致
			if (StringUtil.isNotEmpty(oldPwd)) {
				if (!newPwd.equals(oldPwd)) {
					user.setPassword(newPwd);
					user.setCustomerId(appUser.getCustomerId());
					int isSuccess = appUserDao.updateAppUserPwd(user);
					if (isSuccess > 0) {
						result.setSuccess();
						logger.info("更新用户密码成功!");
					} else {
						logger.info("更新用户密码失败!");
					}
				} else {
					result.setMsg(ServiceConstants.APPUSER_UPDATE_PWD_COMMON_MSG);
					result.setCode(ServiceConstants.APPUSER_UPDATE_PWD_COMMON_CODE);
				}
			}
		}
		return result;
	}
}