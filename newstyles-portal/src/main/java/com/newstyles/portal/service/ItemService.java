package com.newstyles.portal.service;

import com.newstyles.protal.pojo.ItemInfo;

public interface ItemService {

	ItemInfo getItemById(long itemId);
	String getItemDescById(long itemId);
	String getItemParam(long itemId);
}
