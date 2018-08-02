package com.newstyles.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public  NewstylesResult getItemBaseInfo(@PathVariable long itemId){
		NewstylesResult result = itemService.getItemInfo(itemId);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public  NewstylesResult getItemDesc(@PathVariable long itemId){
		NewstylesResult result = itemService.getItemDesc(itemId);
		return result;
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public  NewstylesResult getItemParam(@PathVariable long itemId){
		NewstylesResult result = itemService.getItemParam(itemId);
		return result;
	}
}
