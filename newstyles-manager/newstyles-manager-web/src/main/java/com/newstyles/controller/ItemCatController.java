package com.newstyles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.EUTreeNode;
import com.newstyles.service.ItemCatService;


@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	/*
	 * 商品分类管理
	 */
	@Autowired
	private ItemCatService itemCatService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list")//映射到list
	@ResponseBody
	//保证参数传递包含值，不会出现null的情况
	public List<EUTreeNode> categoryList(@RequestParam(value="id", defaultValue="0") Long parentId) throws Exception {
		//查询数据库
		List<EUTreeNode> catList = itemCatService.getItemCatList(parentId);
		return catList;
	}


}
