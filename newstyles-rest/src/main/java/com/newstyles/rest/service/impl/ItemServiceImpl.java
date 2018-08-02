package com.newstyles.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.JsonUtils;
import com.newstyles.mapper.TbItemDescMapper;
import com.newstyles.mapper.TbItemMapper;
import com.newstyles.mapper.TbItemParamItemMapper;
import com.newstyles.pojo.TbItem;
import com.newstyles.pojo.TbItemDesc;
import com.newstyles.pojo.TbItemParamItem;
import com.newstyles.pojo.TbItemParamItemExample;
import com.newstyles.pojo.TbItemParamItemExample.Criteria;
import com.newstyles.rest.dao.JedisClient;
import com.newstyles.rest.service.ItemService;


@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	
	@Override
	public NewstylesResult getItemInfo(long itemId) {

		// 从缓存中取得对应id商品信息
		try{
			String json = jedisClient.get(REDIS_ITEM_KEY +":" + itemId + ":base");
			
			if(!StringUtils.isBlank(json)){
				//把json转换为java对象
				TbItem item =  JsonUtils.jsonToPojo(json, TbItem.class);
				return NewstylesResult.ok(item);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		// 根据ID查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		try{	
			// 把商品信息写入缓存
			// 重点是设置key的有效期,Hash 不能独立设置过期时间，
			// 可以采用String，可采用key的命名方式
			//e.g. REDIS_ITEM_KEY:商品id:base=json     基本信息
			jedisClient.set(REDIS_ITEM_KEY +":" + itemId + ":base", JsonUtils.objectToJson(item));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY +":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		}catch (Exception e){
			e.printStackTrace();
		}

		return NewstylesResult.ok(item);
	}

	@Override
	public NewstylesResult getItemDesc(long itemId) {

		//添加缓存
		try{
			String json = jedisClient.get(REDIS_ITEM_KEY +":" + itemId + ":desc");
			
			if(!StringUtils.isBlank(json)){
				//把json转换为java对象
				TbItemDesc itemDesc =  JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return NewstylesResult.ok(itemDesc);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		// 创建查询条件
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		
		try{	
			// 把商品信息写入缓存
			// 重点是设置key的有效期,Hash 不能独立设置过期时间，
			// 可以采用String，可采用key的命名方式
			//e.g. REDIS_ITEM_KEY:商品id:base=json     基本信息
			jedisClient.set(REDIS_ITEM_KEY +":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY +":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		}catch (Exception e){
			e.printStackTrace();
		}

		return NewstylesResult.ok(itemDesc);
	}

	@Override
	public NewstylesResult getItemParam(long itemId) {
		
		//添加缓存
		try{
			String json = jedisClient.get(REDIS_ITEM_KEY +":" + itemId + ":param");
			
			if(!StringUtils.isBlank(json)){
				//把json转换为java对象
				TbItemParamItem itemParamItem =  JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return NewstylesResult.ok(itemParamItem);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		// 根据商品的id查询商品的参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		
		if(list != null && list.size() > 0){
			TbItemParamItem paramItem = list.get(0);
			try{	
				// 把商品信息写入缓存
				// 重点是设置key的有效期,Hash 不能独立设置过期时间，
				// 可以采用String，可采用key的命名方式
				//e.g. REDIS_ITEM_KEY:商品id:base=json     基本信息
				jedisClient.set(REDIS_ITEM_KEY +":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY +":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			}catch (Exception e){
				e.printStackTrace();
			}
			return NewstylesResult.ok(paramItem);
		}
		return NewstylesResult.build(400, "无此商品信息规格");
	}
	
}
