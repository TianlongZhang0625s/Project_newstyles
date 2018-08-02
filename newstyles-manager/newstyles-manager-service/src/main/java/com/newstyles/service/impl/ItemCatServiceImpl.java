package com.newstyles.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.EUTreeNode;
import com.newstyles.mapper.TbItemCatMapper;
import com.newstyles.pojo.TbItemCat;
import com.newstyles.pojo.TbItemCatExample;
import com.newstyles.pojo.TbItemCatExample.Criteria;
import com.newstyles.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<EUTreeNode> getItemCatList (long parentid) throws Exception{
		TbItemCatExample example = new TbItemCatExample();
		
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List<EUTreeNode> trList =  new ArrayList<EUTreeNode>();
		for(TbItemCat itemCat : list){
			EUTreeNode treenode = new EUTreeNode();
			treenode.setId(itemCat.getId());
			treenode.setText(itemCat.getName());
			if(itemCat.getIsParent()){
				treenode.setState("closed");
			}else{
				treenode.setState("open");
			}
			
			trList.add(treenode);
		}
		return trList;
	}

}
