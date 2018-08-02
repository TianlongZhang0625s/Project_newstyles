package com.newstyles.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newstyles.portal.service.ContentService;



@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String adjson = contentService.getContentList();
		System.out.println(adjson);
		model.addAttribute("ad1",adjson);
		return "index";
	}
	
//	@RequestMapping(value="/httpClient/post",method =RequestMethod.POST)
//	@ResponseBody
//	public String testPost(String username,String password){
//		return "username"+username + "\tpassword" + password;
//	}
}
