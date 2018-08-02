package com.newstyles.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;


public class FTPTest {


	public void testFtpClient() throws Exception{
		//创建一个FtpClient
		FTPClient ftpClient = new FTPClient();
		//创建Ftp连接
		ftpClient.connect("192.168.59.128",21);
		//登陆Ftp服务器，使用账号和密码
		ftpClient.login("ftpuser", "woaichiyulei1314");
		//上传文件
		//读取本地文件
		FileInputStream inputStream = new FileInputStream(new File("F:\\2017E8018061008.jpg"));
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/ww/images/");
		//设置文件上传格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE );
		//第一参数，服务器端的文件名。
		//第二个参数，上传文件的IO流
		ftpClient.storeFile("Hello1.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
	}
}
