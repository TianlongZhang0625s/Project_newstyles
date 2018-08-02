package com.newstyles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.EUTreeNode;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author Zhangtianlong
 *
 */
@Controller
@RequestMapping("/content")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCargoryService;
	
	@RequestMapping("/category/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0") long parentId){
		List<EUTreeNode> list = contentCargoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/category/create")
	@ResponseBody
	public NewstylesResult createContentCategory(long parentId,String name){
		NewstylesResult result = contentCargoryService.insertContentCategory(parentId, name);
		return result;
	}
	//已修改，测试完成，结果符合预期
	@RequestMapping("/category/delete")
	@ResponseBody
	public NewstylesResult deleteContentCategory(long id ){
		NewstylesResult result = contentCargoryService.deleteContentCategory( id);
		return result;
	}
	
	@RequestMapping("/category/update")
	@ResponseBody
	public NewstylesResult updateContentCategoryName(long id ,String name){
		NewstylesResult result = contentCargoryService.updateContentCategoryName(id, name);
		return result;
	}
}
