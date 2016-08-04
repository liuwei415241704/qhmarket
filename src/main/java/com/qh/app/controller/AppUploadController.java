package com.qh.app.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.qh.app.constant.ClientConstants;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.AppUser;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.resource.file.entity.FTPConfig;
import com.qh.app.resource.file.service.CustomMultipartResolver;
import com.qh.app.resource.file.util.FTPUtils;
import com.qh.app.service.AppUserService;
import com.qh.app.util.PathUtil;
import com.qh.app.util.RedisUtil;
import com.qh.app.util.StringUtil;

/**
 * 上传头像
 * 
 * @author lw
 *
 */

@Controller
@RequestMapping({ "/upload" })
public class AppUploadController {
	
	private static Logger logger = LoggerFactory
			.getLogger(AppUploadController.class);
	
	@Resource
	private AppUserService appUserService;

	@Resource
	private FTPConfig ftpConfig;

	/**
	 * 上传用户头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/userPortrait" })
	@ResponseBody
	public ResponseService uploadUserFile(HttpServletRequest request,
			HttpServletResponse response) {
		String token = request.getParameter("token");
		String userid = request.getParameter("userid");
		String _tokenValue = RedisUtil.get(userid);
		ResponseService result = new ResponseService();
		if (StringUtil.isEmpty(token) || StringUtil.isEmpty(_tokenValue)
				|| !_tokenValue.equals(token)) { // 非法请求
			result.setMsg(ServiceConstants.INVALID_TOKEN_MSG);
			result.setCode(ServiceConstants.INVALID_TOKEN_CODE);
			return result;
		}
		Map<String, String> nameMap = new HashMap<String, String>();
		boolean uploadIsOk = false;
		uploadIsOk = handleUserUploadFile(request, userid, nameMap);
		if (uploadIsOk) {
			AppUser user = new AppUser();
			user.setCustomerId(userid);
			user.setPortrait(ClientConstants.UPLOAD_IMAGE_PATH + userid + "/"
					+ nameMap.get(userid));
			logger.info("新的文件名:{}", nameMap.get(userid));
			logger.info("用户头像路径:" + user.getPortrait());
			// 更新之前获取之前的图片路径,删掉图片
			result = appUserService.updatePortrait(user);
		}
		return result;
	}


	public boolean handleUserUploadFile(HttpServletRequest request,
			String userid, Map<String, String> nameMap) {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CustomMultipartResolver(
				request.getSession().getServletContext());
		boolean uploadIsOk = false;
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					String fileName = "";
					String fileType = ".jpg";
					// 判断文件后缀名
					boolean isHasExtension = PathUtil.hasExtension(myFileName);
					if (isHasExtension) {
						fileType = PathUtil.getExtension(myFileName);
						logger.info("文件格式为:{}", fileType);
					}
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (StringUtil.isNotEmpty(myFileName)) {
						logger.info("上传的文件名:{}", myFileName);
						// 重命名上传后的文件名 以userid为文件名,保证该用户只有一个头像
						fileName = System.currentTimeMillis() + fileType;
						// File localFile = FileUtil.createFile(path);
						nameMap.put(userid, fileName);
						try {
							logger.info("重命名后的文件名:{}", fileName);
							logger.info("server:{}", ftpConfig.getFtpServer());
							// 根据不同的用户创建文件夹
							if (StringUtil.isNotEmpty(userid)) {
								uploadIsOk = FTPUtils
										.getInstance(ftpConfig)
										.storeFile(
												ServiceConstants.UPLOAD_FILE_PATH + "/" + userid,
												fileName, file.getInputStream());
								// 记录上传该文件后的时间
								int finaltime = (int) System
										.currentTimeMillis();
								logger.info("上传所用时间:{}", (finaltime - pre));

							} else {
								return uploadIsOk;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
			// 退出FTP
			FTPUtils.getInstance(ftpConfig).logout();
		}
		return uploadIsOk;
	}
	
}
