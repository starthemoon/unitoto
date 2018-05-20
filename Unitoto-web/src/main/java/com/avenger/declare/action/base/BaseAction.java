package com.avenger.declare.action.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * action基类
 * 
 * @version 1.0
 * @author zhl
 */
public class BaseAction {
	
	/**
	 * 获取请求参数map
	 * @return 请求参数map
	 */
	protected Map<String,Object> getConvertParamMap(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Map<String,String[]> paramMap=request.getParameterMap();
		if(paramMap==null){
			return null;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		for(Map.Entry<String, String[]> entry:paramMap.entrySet()){
			String key=entry.getKey();
			if("_".equals(key)){//忽略ajax请求时传如的时间戳参数
				continue;
			}
			String[] value=entry.getValue();
			if(value!=null&&!StringUtils.isEmpty(value[0])){
				map.put(key, value[0]);
			}
		}
		return map;
	}
}
