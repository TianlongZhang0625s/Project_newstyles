package com.newstyles.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.HttpClientUtil;
import com.newstyles.portal.service.SearchService;
import com.newstyles.protal.pojo.SearchResult;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	
	
	@Override
	public SearchResult search(String queryString, int page) {
		// 调用newstyles-search工程的服务
		//查询参数
		Map<String,String>  params = new HashMap<>();
		params.put("q", queryString);
		params.put("page",page+"" );
		
		try{
			
			//调用服务
			String reslutjson = HttpClientUtil.doGet(SEARCH_BASE_URL,params);
			//将字符串转换为java对象
			NewstylesResult newstylesResult = NewstylesResult.formatToPojo(reslutjson, SearchResult.class);
			if(newstylesResult.getStatus() == 200){
				SearchResult searchResult = (SearchResult) newstylesResult.getData();
				return searchResult;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
