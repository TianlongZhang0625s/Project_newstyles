package com.newstyles.service;

import java.util.List;

import com.newstyles.common.pojo.EUTreeNode;


public interface ItemCatService {
	
	List<EUTreeNode> getItemCatList(long parentid) throws Exception;

}
