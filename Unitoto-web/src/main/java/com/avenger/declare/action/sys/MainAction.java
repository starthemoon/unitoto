package com.avenger.declare.action.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页action类
 * 
 * @version 1.0
 * @author zhl
 */
@Controller
@RequestMapping("/main")
public class MainAction {
	
	/**
	 * 首页
	 * @return	首页
	 * @throws Exception
	 */
	@RequestMapping
	public String execute() throws Exception{
		return "homepage/main";
	}
}
