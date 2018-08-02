package com.newstyles.rest.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.utils.JsonUtils;
import com.newstyles.rest.pojo.CartResult;
import com.newstyles.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping(value="/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback){
		System.out.println("come in");
		CartResult catResult = itemCatService.queryAllCategory();
		//把pojo拼装成字符串
		String json = JsonUtils.objectToJson(catResult);
		//拼装返回值
		String result = callback + "(" + json + ")";
		return result;
		
	}
}
