package com.newstyles.rest.service;

import com.newstyles.common.pojo.NewstylesResult;

public interface RedisService {

	NewstylesResult syncContent(long contentCid);
}
