package com.newstyles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.pojo.TbItemParam;
import com.newstyles.pojo.TbItemParamExample;
import com.newstyles.service.ItemParamService;

/*
 * 商品规格参数管理
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	//RESTful风格的请求参数
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public NewstylesResult getItemParamByCid(@PathVariable long itemCatId){
		NewstylesResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public NewstylesResult isnertItemParam(@PathVariable long cid,String paramData){
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		NewstylesResult result = 	itemParamService.insertItemParam(itemParam);
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public 	EUDataGridResult getItemParamList(@RequestParam(defaultValue = "1") Integer pages,
			@RequestParam(defaultValue = "1") Integer rows) throws Exception{
		EUDataGridResult result = itemParamService.getItemParamList(pages, rows);
		return result;
	}

}
