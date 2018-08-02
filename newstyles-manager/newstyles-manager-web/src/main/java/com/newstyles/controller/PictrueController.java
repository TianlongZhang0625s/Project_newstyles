package com.newstyles.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.newstyles.common.utils.JsonUtils;
import com.newstyles.common.utils.PictureResult;
import com.newstyles.service.PictureService;

@Controller
@RequestMapping("/pic")
public class PictrueController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) throws Exception{
		PictureResult pictureResult = pictureService.uploadFile(uploadFile);
		//为保证功能的兼容，需要将result转换成字符串
		String json = JsonUtils.objectToJson(pictureResult);
		return json;
		
	}
}
