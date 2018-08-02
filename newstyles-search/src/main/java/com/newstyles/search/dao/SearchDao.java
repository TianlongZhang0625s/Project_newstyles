package com.newstyles.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.newstyles.search.pojo.SearchResult;

public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception ;
}
