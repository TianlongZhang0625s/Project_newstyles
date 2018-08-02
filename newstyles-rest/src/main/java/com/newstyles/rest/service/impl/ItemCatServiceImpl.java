package com.newstyles.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newstyles.mapper.TbItemCatMapper;
import com.newstyles.pojo.TbItemCat;
import com.newstyles.pojo.TbItemCatExample;
import com.newstyles.pojo.TbItemCatExample.Criteria;
import com.newstyles.rest.pojo.CartResult;
import com.newstyles.rest.pojo.CatNode;
import com.newstyles.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public CartResult queryAllCategory() {
		CartResult cartResult = new CartResult();
		//查询分类列表
		cartResult.setData(getItemCatList(0));
		return cartResult;
	}

	private List<?> getItemCatList(long parentId){
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList(); 
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			//判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if(parentId ==0){
					catNode.setName("<a href= '/products/" + tbItemCat.getId() + ".html'>"+tbItemCat.getName() +"</a>");					
				}else{
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/ "+tbItemCat.getId()+".html");
				//递归调用
				catNode.setItem(getItemCatList(tbItemCat.getId()));
				resultList.add(catNode);	
				count ++;
				//第一层仅取14条记录
				if(parentId ==0 && count >=14){
					break;
				}
		}else{
			resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
		}
		}
		return resultList;

	}
}
