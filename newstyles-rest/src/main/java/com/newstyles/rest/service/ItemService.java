package com.newstyles.rest.service;

import com.newstyles.common.pojo.NewstylesResult;

public interface ItemService {

	NewstylesResult getItemInfo(long itemId);
	NewstylesResult getItemDesc(long itemId);
	NewstylesResult getItemParam(long itemId);
}
