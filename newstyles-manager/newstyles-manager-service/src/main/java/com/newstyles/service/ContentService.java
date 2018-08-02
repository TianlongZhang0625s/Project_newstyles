package com.newstyles.service;

import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.pojo.TbContent;

public interface ContentService {

	 EUDataGridResult getContentCateGoryList(long categoryId,int page, int rows);
	 //插入逻辑
	NewstylesResult insertContent(TbContent tbContent);
	//（编辑）更新逻辑(/rest/content/edit)
	//NewstylesResult editContent(TbContent tbContent);
	//选择对应类目后删除
	NewstylesResult deleteContentById(String ids);
	//删除对应的Content
	NewstylesResult deleteContentByCatgoryID(long categoryid);
	
}
