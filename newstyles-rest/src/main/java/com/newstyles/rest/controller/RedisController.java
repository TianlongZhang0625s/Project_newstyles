package com.newstyles.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.rest.service.RedisService;

/**
 * 缓存同步Controller
 * @author Zhangtianlong
 *
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public NewstylesResult contentCacheSync(@PathVariable long contentCid ){
		NewstylesResult result = redisService.syncContent(contentCid);
		return result;
	}
	

}
