package com.newstyles.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.newstyles.common.pojo.NewstylesResult;
import com.newstyles.common.utils.HttpClientUtil;
import com.newstyles.common.utils.JsonUtils;
import com.newstyles.pojo.TbContent;


public class HttpClientTest {

	public void doGet() throws Exception{
		//创建一个httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个GET对象
		HttpGet get = new HttpGet("http://www.sougou.com");
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//相应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		//将entity的内容读入到一个字符串里面
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		//关闭请求
		response.close();
		httpClient.close();

	}
	//@Test
	public void doGetWithParam() throws Exception{
		
		//创建可关闭的对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个uri
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("query", "花千骨");
		HttpGet get = new HttpGet(uriBuilder.build());
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//相应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		//将entity的内容读入到一个字符串里面
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		//关闭请求
		response.close();
		httpClient.close();
	}
	
//	@Test
	public void doPost() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个post对象
		HttpPost post = new HttpPost("http://localhost:8082/httpClient/post.html");
		//执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		String string = EntityUtils.toString(response.getEntity());
		System.out.println(string);
		response.close();
		httpClient.close();
	}
	
	
	public void test(){
		String REST_BASE_URL = "http://localhost:8081/rest/";
		String REST_INDEX_AD_URL = "content/list/89";
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		System.out.println(result);
		//把字符串转换成NEWSTYLESRESULT
		try{
			NewstylesResult newstylesResult = NewstylesResult.formatToList(result, TbContent.class);
			//取出内容列表
			List<TbContent> list = (List<TbContent>) newstylesResult.getData();
			List<Map> reslultList = new ArrayList<>();
			//创建jsp页面要求的pojolist列表
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				reslultList.add(map);
			}
			String sss = JsonUtils.objectToJson(reslultList);
			System.out.println(sss);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
