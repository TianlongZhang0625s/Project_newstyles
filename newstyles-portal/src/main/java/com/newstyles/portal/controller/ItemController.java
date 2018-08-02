package com.newstyles.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.portal.service.ItemService;
import com.newstyles.protal.pojo.ItemInfo;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable long itemId,Model model){
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item",item);
		return "item";
	}
	
	//防止doget得到的内容乱码
	@RequestMapping(value = "/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable long itemId){
		String string = itemService.getItemDescById(itemId);
		return string;
	} 
	
	//防止doget得到的内容乱码
	@RequestMapping(value = "/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable long itemId){
		String string = itemService.getItemParam(itemId);
		return string;
	} 
}
