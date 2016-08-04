package com.qh.app.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.qh.app.constant.ClientConstants;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.AppAttach;
import com.qh.app.entity.AppMarket;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.resource.file.entity.FTPConfig;
import com.qh.app.resource.file.service.CustomMultipartResolver;
import com.qh.app.resource.file.util.FTPUtils;
import com.qh.app.service.AppAttachService;
import com.qh.app.service.AppMarketService;
import com.qh.app.util.JSONCommUtils;
import com.qh.app.util.PathUtil;
import com.qh.app.util.RedisUtil;
import com.qh.app.util.StringUtil;

/**
 * 跳蚤市场发布查询
 * 
 * @author lw
 * 
 */
@RequestMapping({ "/market" })
@Controller
public class AppMarketController {

	private static Logger logger = LoggerFactory
			.getLogger(AppMarketController.class);

	@Resource
	private AppMarketService appMarketService;

	@Resource
	private FTPConfig ftpConfig;

	@Resource
	private AppAttachService appAttachService;

	/**
	 * 新增市场单品信息
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = { "/addKinds" })
	@ResponseBody
	public ResponseService uploadMarketFile(HttpServletRequest request,
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
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String tel = request.getParameter("tel");
		String address = request.getParameter("adress");
		String describe = request.getParameter("describe");
		String contacts = request.getParameter("contacts");
		String kind = request.getParameter("kind");
		AppMarket appMarket = new AppMarket();
		appMarket.setName(name);
		appMarket.setPrice(new BigDecimal(price));
		appMarket.setTel(tel);
		appMarket.setAddress(address);
		appMarket.setDescribe(describe);
		appMarket.setContacts(contacts);
		appMarket.setKind(kind);
		appMarket.setCreator(userid);
		result = appMarketService.addKinds(appMarket);
		if (ServiceConstants.SUCCESS_CODE.equals(result.getCode())) {
			String marketId = (String) result.getData();
			// 新增市场文字信息
			Map<String, String> nameMap = new HashMap<String, String>();
			boolean uploadIsOk = false;
			uploadIsOk = handleMarketUploadFile(request, marketId, nameMap);
			if (uploadIsOk) {
				appMarket.setMarketId(marketId);
				appMarket
						.setPortrait(ClientConstants.UPLOAD_MARKET_PATH
								+ marketId
								+ "/"
								+ nameMap
										.get(ClientConstants.MARKET_FILE_TYPE_PORTRAIT));
				// 更新市场信息头图
				result = appMarketService.updatePortrait(appMarket);
				// 新增其他附件图片路径信息
				if (ServiceConstants.SUCCESS_CODE.equals(result.getCode())) {
					Set set = nameMap.keySet();
					Iterator<String> iter = set.iterator();
					while (iter.hasNext()) {
						String orderFlag = iter.next();
						if (!ClientConstants.MARKET_FILE_TYPE_PORTRAIT.equals(orderFlag)) {
							AppAttach appAttach = new AppAttach();
							appAttach.setCreator(userid);
							appAttach.setGoodsId(marketId);
							appAttach.setOrderFlag(orderFlag);
							appAttach
									.setType(ServiceConstants.ATTACH_TYPE_MARKET);
							appAttach
									.setUrl(ClientConstants.UPLOAD_MARKET_PATH
											+ marketId
											+ "/"
											+ nameMap.get(orderFlag));
							result = appAttachService.addAttach(appAttach);
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * 根据市场类别查询市场单品信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getKinds" })
	@ResponseBody
	public ResponseService getKinds(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据分类查询单品信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppMarket.class, requestData);
		AppMarket appMarket = (AppMarket) requestData.getBody();
		return appMarketService.queryKindsByKindName(appMarket, page);
	}

	public boolean handleMarketUploadFile(HttpServletRequest request,
			String marketId, Map<String, String> nameMap) {
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
				String keyString = iter.next();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(keyString);
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
						// 重命名上传后的文件名 以marketId为文件名,保证该用户只有一个头像
						fileName = System.currentTimeMillis() + fileType;
						// File localFile = FileUtil.createFile(path);
						nameMap.put(keyString, fileName);
						try {
							logger.info("重命名后的文件名:{}", fileName);
							logger.info("server:{}", ftpConfig.getFtpServer());
							// 根据不同的用户创建文件夹
							if (StringUtil.isNotEmpty(marketId)) {
								uploadIsOk = FTPUtils
										.getInstance(ftpConfig)
										.storeFile(
												ServiceConstants.UPLOAD_FILE_MARKET_PATH
														+ "/" + marketId,
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
