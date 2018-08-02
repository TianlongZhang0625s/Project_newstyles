package com.newstyles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.pojo.TbItem;
import com.newstyles.service.ItemService;




@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
//	配置url和方法的映射关系
	@RequestMapping("/item/{itemId}")
	//返回一个json数据
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page,Integer rows){
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value ="item/save",method = RequestMethod.POST)
	@ResponseBody
	//修改createItem方法
	private NewstylesResult createItem(TbItem item,String desc,String itemParams) throws Exception{
		NewstylesResult result = itemService.createItem(item,desc,itemParams);
		return result;
	}

}
