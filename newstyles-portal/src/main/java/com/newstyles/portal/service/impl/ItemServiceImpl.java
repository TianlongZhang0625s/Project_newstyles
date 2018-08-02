package com.newstyles.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.HttpClientUtil;
import com.newstyles.common.utils.JsonUtils;
import com.newstyles.pojo.TbItemDesc;
import com.newstyles.pojo.TbItemParamItem;
import com.newstyles.portal.service.ItemService;
import com.newstyles.protal.pojo.ItemInfo;

@Service
public class ItemServiceImpl implements ItemService{

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	
	@Override
	public ItemInfo getItemById(long itemId) {
		// 调用Rest的服务查询商品信息
		try{
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if(!StringUtils.isBlank(json)){
				NewstylesResult newstylesResult = NewstylesResult.formatToPojo(json,ItemInfo.class);
				if(newstylesResult.getStatus() == 200){
					ItemInfo item = (ItemInfo) newstylesResult.getData();
					return item;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getItemDescById(long itemId) {
		//
		try{			
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL +itemId );
			NewstylesResult newstylesResult  = NewstylesResult.formatToPojo(json, TbItemDesc.class);
			if(newstylesResult.getStatus() == 200){
				TbItemDesc itemDesc = (TbItemDesc)newstylesResult.getData();
				String result = itemDesc.getItemDesc();
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getItemParam(long itemId) {
		
		try{
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			NewstylesResult newstylesResult = NewstylesResult.formatToPojo(json, TbItemParamItem.class);
			if(newstylesResult.getStatus() == 200){
				TbItemParamItem itemParam = (TbItemParamItem)newstylesResult.getData();
				String paramData = itemParam.getParamData();
				
				//生成html
				//把json数据转换成java对象
				List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
				//将参数信息转换成html
				StringBuffer sb = new StringBuffer(); 
				//sb.append("<div>");
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for (Map map : paramList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> params = (List<Map>) map.get("params");
					for (Map map2 : params) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
						sb.append("            <td>"+map2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				//返回html
				return sb.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
