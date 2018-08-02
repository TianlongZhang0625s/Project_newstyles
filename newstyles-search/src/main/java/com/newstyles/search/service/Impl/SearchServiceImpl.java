package com.newstyles.search.service.Impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newstyles.search.dao.SearchDao;
import com.newstyles.search.pojo.SearchResult;
import com.newstyles.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页
		query.setStart((page-1) * rows);
		query.setRows(rows);
		//设置高亮显示,及搜索区域
		query.set("df", "item_keywords");
		
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("<em>");
		
		//执行查询
		SearchResult searchResult = searchDao.search(query);
		
		//计算查询总页数
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount/rows;
		
		if(recordCount % rows > 0){
			pageCount ++;
		}
		
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		
		return searchResult;
		
	}

	
}
