package com.newstyles.service;

import java.util.List;

import com.newstyles.common.pojo.EUTreeNode;
import com.newstyles.common.pojo.NewstylesResult;


public interface ContentCategoryService {

	List<EUTreeNode>  getCategoryList(long parentId);
	NewstylesResult insertContentCategory(long parentId,String name);
	NewstylesResult deleteContentCategory(long id);
	NewstylesResult updateContentCategoryName(long id,String name);	
}
