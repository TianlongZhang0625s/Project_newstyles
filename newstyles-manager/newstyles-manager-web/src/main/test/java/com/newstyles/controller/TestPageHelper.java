package com.newstyles.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newstyles.mapper.TbItemMapper;
import com.newstyles.pojo.TbItem;
import com.newstyles.pojo.TbItemExample;


public class TestPageHelper {
	
	public void testPageHelper(){
//		创建一个spring容器
		ApplicationContext applicaitonContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从spring中获取mapper对象
		TbItemMapper mapper = applicaitonContext.getBean(TbItemMapper.class);
		//执行查询，并分页
		TbItemExample example = new TbItemExample();

		//分页处理
		PageHelper.startPage(1, 10);
		List<TbItem> list = 	mapper.selectByExample(example);
		
		//枚举取得对应的记录
		for(TbItem tbItem : list){
			System.out.println(tbItem.getTitle());
		}
		
		//取得分页信息
		PageInfo <TbItem> pageInfo = new PageInfo(list);
		long total = pageInfo.getTotal();
		System.out.println("共有商品：" + total + "件。");

	}

}
