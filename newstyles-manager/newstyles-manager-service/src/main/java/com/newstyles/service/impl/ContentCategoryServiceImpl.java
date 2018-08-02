package com.newstyles.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newstyles.common.pojo.EUTreeNode;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.mapper.TbContentCategoryMapper;
import com.newstyles.mapper.TbContentMapper;
import com.newstyles.pojo.TbContentCategory;
import com.newstyles.pojo.TbContentCategoryExample;
import com.newstyles.pojo.TbContentCategoryExample.Criteria;
import com.newstyles.pojo.TbContentExample;
import com.newstyles.service.ContentCategoryService;
import com.newstyles.service.ContentService;

/**
 * 分类管理
 * @author Zhangtianlong
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Autowired
	private ContentService contentService;
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList();
		for (TbContentCategory tbContentCategory : list){
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public NewstylesResult insertContentCategory(long parentId, String name) {
		// TODO Auto-generated method stub
//		创建一个pojo
		TbContentCategory contentCartgory = new TbContentCategory();
		//注意修改mapper文件*******
		//在插入数据之后才可以取到
		contentCartgory.setName(name);
		contentCartgory.setIsParent(false);
		//状态：1：正常，2删除
		contentCartgory.setStatus(1);
		contentCartgory.setParentId(parentId);
		contentCartgory.setSortOrder(1);
		contentCartgory.setCreated(new Date());
		contentCartgory.setUpdated(new Date());
		
		  contentCategoryMapper.insert(contentCartgory);
		  //查看父节点的isParent的属性是否为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			parentCat.setIsParent(true);
			//跟新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
			
		}
		return NewstylesResult.ok(contentCartgory) ;
	}

	@Override
	public NewstylesResult deleteContentCategory(long id) {
		 TbContentCategory tbContent = contentCategoryMapper.selectByPrimaryKey(id);
		//获取父节点值
		 long parentId = tbContent.getParentId();
		 TbContentCategoryExample tbContentExample = new TbContentCategoryExample();
		 Criteria criteria = tbContentExample.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		 
		 //删除的是子节点的情况
		 if(!tbContent.getIsParent()){
			 //同时应该删除对应的广告的具体的内容（Content）包括图片
			 contentService.deleteContentByCatgoryID(id);	
			 //删除对应节子节点
			 contentCategoryMapper.deleteByPrimaryKey(id);
	 
			 int count = contentCategoryMapper.countByExample(tbContentExample);
			 if(count == 0){
				  //查看父节点的isParent的属性是否为true
					TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
					if(parentCat.getIsParent()){
						parentCat.setIsParent(false);
						parentCat.setUpdated(new Date());
						//跟新父节点
						contentCategoryMapper.updateByPrimaryKey(parentCat);					
					}
			 }
		 }else{
			 //删除节点为父节点的情况
			 TbContentCategoryExample example = new TbContentCategoryExample();
			 Criteria criterias = example.createCriteria();
			 criterias.andParentIdEqualTo(id);
			 List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			 //删除对应的图片的内容
			 for (TbContentCategory tbContentCategory : list) {
				 contentService.deleteContentByCatgoryID(tbContentCategory.getId());	
				 contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
			}
			 
			 //删除父节点
			 contentCategoryMapper.deleteByPrimaryKey(id);
		 }
		return NewstylesResult.ok();
	}

	@Override
	public NewstylesResult updateContentCategoryName(long id, String name) {
		//查询到对应的节点
		TbContentCategory tbContentCat = contentCategoryMapper.selectByPrimaryKey(id);
		tbContentCat.setName(name);
		tbContentCat.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKey(tbContentCat);	
		return NewstylesResult.ok();
	}
	

}
