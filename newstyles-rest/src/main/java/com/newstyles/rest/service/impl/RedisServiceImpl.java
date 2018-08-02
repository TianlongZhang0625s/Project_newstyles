package com.newstyles.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.ExceptionUtil;
import com.newstyles.rest.dao.JedisClient;
import com.newstyles.rest.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public NewstylesResult syncContent(long contentCid) {
		try{
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid+"");	
		}catch(Exception e){
			e.printStackTrace();
			return NewstylesResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return NewstylesResult.ok();
	}
}
