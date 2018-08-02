package com.newstyles.service;



import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	//其对含有拼接或者是复发SQL的处理不好
	//再者，其对于mybatis逆向工程的支持也不太完美
	EUDataGridResult getItemList(int page,int rows);
	
	//添加商品功能的业务逻辑
	NewstylesResult createItem(TbItem item,String desc,String itemParams) throws Exception;
}
