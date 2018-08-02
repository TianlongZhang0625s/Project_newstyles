package com.newstyles.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.HttpClientUtil;
import com.newstyles.common.utils.JsonUtils;
import com.newstyles.pojo.TbContent;
import com.newstyles.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	//一定要注意取值的形式	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
		
	@Override
	public String getContentList(){
		//调用系统服务
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		//把字符串转换成NEWSTYLESRESULT
		try{
			NewstylesResult newstylesResult = NewstylesResult.formatToList(result, TbContent.class);

			//取出内容列表
			List<TbContent> list = (List<TbContent>) newstylesResult.getData();
			List<Map> reslultList = new ArrayList<>();
			//创建jsp页面要求的pojolist列表
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				reslultList.add(map);
			}
			return JsonUtils.objectToJson(reslultList);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
