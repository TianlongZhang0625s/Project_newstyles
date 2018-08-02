package com.newstyles.service.impl;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newstyles.common.utils.FtpUtil;
import com.newstyles.common.utils.IDUtils;
import com.newstyles.common.utils.PictureResult;
import com.newstyles.service.PictureService;


@Service
public class PictureServiceImpl implements PictureService{

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;


	@Override
	public PictureResult uploadFile(MultipartFile uploadFile) throws Exception {
	    
		//上传文件功能实现
		String path = savePicture(uploadFile);
		
		//返回显示
		PictureResult result = new PictureResult(0, IMAGE_BASE_URL + path);
		
		return result;
		
	}
	
	private String savePicture(MultipartFile uploadFile){
		String result = null;
		try{
			if(uploadFile.isEmpty())
				return null;
			String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date())
						+ "/" + new SimpleDateFormat("MM").format(new Date()) + "/"
						+new SimpleDateFormat("dd").format(new Date());
			//读取原始文件的名字
			String originalFilename = uploadFile.getOriginalFilename();
			//构建新的文件的名字
			String newName = IDUtils.getImageName() + originalFilename.substring(originalFilename.lastIndexOf("."));
			//转存文件，上传到ftp;
			boolean fileflage = uploadFile.isEmpty();
			FileInputStream uploads = (FileInputStream)uploadFile.getInputStream();
			System.out.println(fileflage);
			boolean flag = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, filePath, newName, 
					uploads);
			System.out.println(flag);
			result = filePath + "/" + newName;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean deleteFilesPic(String fileUrl)  {
		
		String[] parmas = pathAndFileName(fileUrl);
		boolean result = false;
		if(null != parmas){
			result = FtpUtil.deleteFiles(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, 
					FTP_PASSWORD,FTP_BASE_PATH, parmas);
		}		
		return false;
	}
	
	

	
	private String[] pathAndFileName(String url){
		//判断是否为空进行处理
		if(!url.isEmpty()){
			String[] temp = url.split("/");
			String[] param = {temp[4],temp[5],temp[6],temp[7]};
			return param;
		}
		return null;
	}
//	@Test
//	public void testr(){
//		String url = "http://192.168.59.128/images/2018/04/21/1524245302670104.jpg";
//		if(!url.isEmpty()){
//			String[] temp = url.split("/");
//			String[] param = {"/"+temp[4],"/"+temp[5],"/"+temp[6],"/"+temp[7]};
//			for (String string : param) {
//				System.out.println(string);
//				
//			}
//		}
//	}
}
