package com.qh.app.resource.file.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.qh.app.constant.ServiceConstants;
import com.qh.app.resource.file.entity.Progress;


/**
 * 文件上传进度监听器
 * 
 * @author lw
 * 
 */
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;

	public FileUploadProgressListener() {
	}

	public FileUploadProgressListener(HttpSession session) {
		this.session = session;
		Progress status = new Progress();
		session.setAttribute("upload_ps", status);
	}

	/**
	 * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Progress status = (Progress) session
				.getAttribute(ServiceConstants.UPLOAD_FILE_PROGRESS);
		status.setBytesRead(pBytesRead);
		status.setContentLength(pContentLength);
		status.setItems(pItems);
		session.setAttribute("upload_ps", status);
	}

}
