package com.newstyles.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.mapper.TbItemParamMapper;
import com.newstyles.pojo.TbItemParam;
import com.newstyles.pojo.TbItemParamExample;
import com.newstyles.pojo.TbItemParamExample.Criteria;
import com.newstyles.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public NewstylesResult getItemParamByCid(long cid){
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否有结果
		if(list != null && list.size() > 0){
			return NewstylesResult.ok(list.get(0));
		}
		return NewstylesResult.ok();
	}

	@Override
	public NewstylesResult insertItemParam(TbItemParam itemParam){
		//补全pojo类
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规格参数表中
		itemParamMapper.insert(itemParam);
		return NewstylesResult.ok();
	}

	@Override
	public EUDataGridResult getItemParamList(Integer page, Integer rows) throws Exception {
		// 分页处理
		PageHelper.startPage(page, rows);
		//查询规格表
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//获取分页信息

		//返回结果
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	
}
