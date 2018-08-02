package com.newstyles.service;

import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.pojo.TbItemParam;

public interface ItemParamService {
	 NewstylesResult getItemParamByCid(long id);
	 NewstylesResult insertItemParam(TbItemParam itemParam);
	 EUDataGridResult getItemParamList(Integer page,Integer rows) throws Exception;
}
