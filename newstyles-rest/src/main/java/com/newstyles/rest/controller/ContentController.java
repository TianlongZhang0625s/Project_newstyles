package com.newstyles.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.ExceptionUtil;
import com.newstyles.pojo.TbContent;
import com.newstyles.rest.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public NewstylesResult getContentList(@PathVariable long contentCategoryId){
		try{
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return NewstylesResult.ok(list);
		}catch(Exception e){
			e.printStackTrace();
			return NewstylesResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
