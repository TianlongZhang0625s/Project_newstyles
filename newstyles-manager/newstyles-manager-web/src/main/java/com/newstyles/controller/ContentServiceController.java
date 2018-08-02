package com.newstyles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.pojo.TbContent;
import com.newstyles.service.ContentService;

@Controller
public class ContentServiceController {
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 查询对应类目的内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return 状态码
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDataGridResult getContentCateGoryList(long categoryId,int page, int rows){
		EUDataGridResult result = contentService.getContentCateGoryList(categoryId,page,rows);
		return result;
	}
	
	/**
	 * 新增功能
	 * @param content 待添加的广告内容类
	 * @return 状态码
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public NewstylesResult insertContent(TbContent content){
		NewstylesResult result = contentService.insertContent(content);
		return result;
	}
	
	/**
	 * 删除功能
	 * @param ids 前端传递的参数，包含选择的id值
	 * @return 返回状态码
	 */
	@RequestMapping(value = "/content/delete")
	@ResponseBody
	public NewstylesResult deleteContentById(String ids){
		NewstylesResult result = contentService.deleteContentById(ids);
		return result;
	}
}
