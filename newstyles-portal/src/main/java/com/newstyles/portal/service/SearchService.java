package com.newstyles.portal.service;

import com.newstyles.protal.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString ,int page);
}
