package com.qh.app.resource.file.entity;

public class FTPConfig {
	private String username;
	private String password;
	private String ftpServer;
	private String ftpUploadPath;
	private String port;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public String getFtpUploadPath() {
		return ftpUploadPath;
	}

	public void setFtpUploadPath(String ftpUploadPath) {
		this.ftpUploadPath = ftpUploadPath;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}



}
