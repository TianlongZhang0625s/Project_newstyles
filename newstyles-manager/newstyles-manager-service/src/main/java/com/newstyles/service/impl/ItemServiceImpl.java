package com.newstyles.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.IDUtils;
import com.newstyles.mapper.TbItemDescMapper;
import com.newstyles.mapper.TbItemMapper;
import com.newstyles.mapper.TbItemParamItemMapper;
import com.newstyles.pojo.TbItem;
import com.newstyles.pojo.TbItemDesc;
import com.newstyles.pojo.TbItemExample;
import com.newstyles.pojo.TbItemExample.Criteria;
import com.newstyles.pojo.TbItemParamItem;
import com.newstyles.service.ItemService;

import sun.tools.jconsole.inspector.Utils.EditFocusAdapter;



@Service  
//添加Service注解，在扫描包时可以将ItemService的实现类对象放到容器中
public class ItemServiceImpl implements ItemService {

	//自动装配，利用spring容器中的itemMaapper对象自动装配
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	//实现类中实现声明的方法
	@Override
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		//
		TbItemExample example = new TbItemExample();
		
		//添加查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		
		if(list != null && list.size() > 0){
			TbItem item = list.get(0);
			return item;
		}
		
		return null;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample exmaple = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(exmaple);
		// TODO Auto-generated method stub
		//创建返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		//获取记录的总数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		
		return result;
	}
	
	@Override
	public NewstylesResult createItem(TbItem item,String desc,String itemParams) throws Exception{
		//将item补全
		//1.生成商品id，可以使用uuid，不过在url中使用时，不美观
		//可以使用IDUtils类生城
		Long itemId = IDUtils.getItemId();
		item.setId(itemId);
		
		//设置商品的状态，1正常，2，下架，3，删除
		item.setStatus((byte)1);
		
		//商品的创建时间和更新时间
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述
		NewstylesResult result = insertItemDesc(itemId, desc);
		if(result.getStatus() != 200){
			throw new Exception();
		}
		/***********************************************************************************/
		//添加商品规格信息
        result = insertParamItem(itemId,itemParams);
		if(result.getStatus() != 200){
			throw new Exception();
		}
		return NewstylesResult.ok();
	}
	

	private NewstylesResult insertItemDesc (long itemId,String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//插入到数据库
		itemDescMapper.insert(itemDesc);
		return NewstylesResult.ok();
	}

	private NewstylesResult insertParamItem(Long itemId,String itemParams){
		//创建新的对象来存储要储存的信息
		TbItemParamItem itemParamItem = new TbItemParamItem();
		//补全商品规格参数中没有完整的字段
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//向表格中插入数据
		itemParamItemMapper.insert(itemParamItem);
		return NewstylesResult.ok();
	}
	
}
