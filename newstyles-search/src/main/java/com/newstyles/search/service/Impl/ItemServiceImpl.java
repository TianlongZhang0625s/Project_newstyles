package com.newstyles.search.service.Impl;


import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.ExceptionUtil;
import com.newstyles.search.mapper.ItemMapper;
import com.newstyles.search.pojo.Item;
import com.newstyles.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public NewstylesResult importAllItem()  {
		
		try{
			
			List<Item> items = itemMapper.getItemList();
			//把商品信息写入索引库
			for (Item item : items) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", item.getId());
				document.addField("item_image", item.getImage());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_category_name", item.getCategory_name());
				document.addField("item_desc", item.getItem_desc());
				//写入索引库
				solrServer.add(document);
			}
			solrServer.commit();
		}catch(Exception e){
			e.printStackTrace();
			return NewstylesResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return NewstylesResult.ok();
	}
}
