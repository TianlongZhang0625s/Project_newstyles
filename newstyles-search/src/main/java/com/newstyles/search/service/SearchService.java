package com.newstyles.search.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.newstyles.search.dao.SearchDao;
import com.newstyles.search.pojo.SearchResult;

public interface SearchService {


	
	SearchResult search(String queryString, int page, int rows) throws Exception;
}
