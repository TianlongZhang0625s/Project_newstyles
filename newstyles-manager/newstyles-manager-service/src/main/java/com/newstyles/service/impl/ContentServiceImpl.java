package com.newstyles.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newstyles.common.pojo.EUDataGridResult;
import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.HttpClientUtil;
import com.newstyles.common.utils.JsonUtils;
import com.newstyles.mapper.TbContentMapper;
import com.newstyles.pojo.TbContent;
import com.newstyles.pojo.TbContentExample;
import com.newstyles.pojo.TbContentExample.Criteria;
import com.newstyles.service.ContentService;
import com.newstyles.service.PictureService;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private PictureService pictureService;	
	//服务相关
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	
	@Override
	public EUDataGridResult getContentCateGoryList(long categoryId,int page, int rows) {
		//查询商品列表
		TbContentExample exmaple = new TbContentExample();
		Criteria criteria = exmaple.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(exmaple);
		// TODO Auto-generated method stub
		//创建返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		//获取记录的总数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public NewstylesResult insertContent(TbContent content) {
		//补全POJO的内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//添加同步缓存逻辑
		try{
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL + content.getCategoryId());		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return NewstylesResult.ok();
	}

	@Override
	public NewstylesResult deleteContentById(String ids){
	     String a[] =ids.split(",");
	     if(a.length != 0){
	    	 for(int i=0;i<a.length;i++){
	    		 TbContent tbContent = contentMapper.selectByPrimaryKey(Long.parseLong(a[i].trim()));
	    		 try {
					pictureService.deleteFilesPic(tbContent.getPic());
					pictureService.deleteFilesPic(tbContent.getPic2());
					HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL + tbContent.getCategoryId());
				} catch (Exception e) {
					e.printStackTrace();
				}		 
	    		 contentMapper.deleteByPrimaryKey(Long.parseLong(a[i].trim()));
	    	 }
	     }else{
    		 return NewstylesResult.build(500,"Misserror");
    	 }
		 return NewstylesResult.ok();
	}
	@Override
	public NewstylesResult deleteContentByCatgoryID(long categoryid){
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryid);
		//删除和CategoryId相同的项进行删除	
		List <TbContent> list = contentMapper.selectByExample(example);
		for (TbContent tbContent : list) {
			
			try {
				pictureService.deleteFilesPic(tbContent.getPic());
				pictureService.deleteFilesPic(tbContent.getPic2());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contentMapper.deleteByPrimaryKey(tbContent.getId());
			
		}
		return NewstylesResult.ok();
	}
}
