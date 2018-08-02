package com.newstyles.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.search.service.ItemService;

@Controller
@RequestMapping("/manager")
public class ItemController {

	/**
	 * 导入所有数据到多音库
	 * 
	 */
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importall")
	@ResponseBody
	public NewstylesResult importAllItem(){
		NewstylesResult result = itemService.importAllItem();
		return result;
		
	}
}
