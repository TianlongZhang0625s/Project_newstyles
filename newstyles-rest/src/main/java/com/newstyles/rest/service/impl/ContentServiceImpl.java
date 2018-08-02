package com.newstyles.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newstyles.common.utils.JsonUtils;
import com.newstyles.mapper.TbContentMapper;
import com.newstyles.pojo.TbContent;
import com.newstyles.pojo.TbContentExample;
import com.newstyles.pojo.TbContentExample.Criteria;
import com.newstyles.rest.dao.JedisClient;
import com.newstyles.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
 
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public List<TbContent> getContentList(long contentCid) {
		//缓存的添加不能影响正常的业务逻辑
		//1.从缓存中取内容
		try{
			String reslut = jedisClient.hget(INDEX_CONTENT_REDIS_KEY,contentCid+"");
			if(!StringUtils.isBlank(reslut)){
				List<TbContent> resultList = JsonUtils.jsonToList(reslut, TbContent.class);
				return resultList;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		//根据内容分类id查询列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//2.向缓存中添加内容
		try{
			//将list转换成字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid+"", cacheString);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

}
