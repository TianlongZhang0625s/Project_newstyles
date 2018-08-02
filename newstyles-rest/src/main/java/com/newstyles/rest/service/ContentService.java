package com.newstyles.rest.service;

import java.util.List;

import com.newstyles.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(long contentCid);
}
