package com.qh.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.qh.app.dao.AppCommentsDao;
import com.qh.app.entity.AppComments;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppCommentsService;
import com.qh.app.util.StringUtil;

@Service("appCommentsService")
public class AppCommentsServiceImpl implements AppCommentsService {

	private static Logger logger = LoggerFactory
			.getLogger(AppCommentsServiceImpl.class);

	@Resource
	private AppCommentsDao appCommentsDao;

	@Override
	public ResponseService addComments(AppComments comments) {
		ResponseService result = new ResponseService();
		int isSuccess = 0;
		String score = comments.getScore();
		if (comments != null && StringUtil.isNotEmpty(score)) {
			// 获取主键
			isSuccess = appCommentsDao.addComments(comments);
			if (isSuccess > 0) {
				logger.info("增加评论成功");
			} else {
				logger.info("增加评论失败");
			}
			// 判断该用户是否已经评论过该商户
//			AppScore isExists = appScoreDao.queryScoreByCustomerId(
//					comments.getCustomerId(), comments.getBusinessId());
//			if (isExists == null) {
//				String scoreId = GenerateUUID.getShortUuid();
//				AppScore appScore = new AppScore();
//				appScore.setScoreId(scoreId);
//				appScore.setCustomerId(comments.getCustomerId());
//				appScore.setBusinessId(comments.getBusinessId());
//				appScore.setScore(new BigDecimal(score));
//				isScoreSuccess = appScoreDao.addScore(appScore);
//				if (isScoreSuccess > 0) {
//					logger.info("增加评分成功");
//				} else {
//					logger.info("增加评分失败");
//				}
//			} else {
//				AppScore appScore = new AppScore();
//				appScore.setCustomerId(comments.getCustomerId());
//				appScore.setBusinessId(comments.getBusinessId());
//				appScore.setScore(new BigDecimal(score));
//				isScoreSuccess = appScoreDao.updateScore(appScore);
//				if (isScoreSuccess > 0) {
//					logger.info("修改评分成功");
//				} else {
//					logger.info("修改评分失败");
//				}
//			}
			if (isSuccess > 0) {
				logger.info("****评论提交成功!****");
				result.setSuccess();
			}
		}
		return result;
	}

	@Override
	public ResponseService queryComments(AppComments coments, Page page) {
		ResponseService result = new ResponseService();
		if (coments != null) {
			if (page != null) {
				PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
			}
			logger.info("****获取评论****");
			List<AppComments> comentsList = appCommentsDao
					.queryComments(coments);
			if (comentsList != null) {
				logger.info("****获取评论成功!****");
				result.setSuccess();
				result.setData(comentsList);
			}
		}
		return result;
	}
}
