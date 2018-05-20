package com.avenger.declare.action.sys;

import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avenger.declare.action.base.BaseAction;
import com.avenger.declare.api.sys.entity.Community;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.CommunityService;
import com.avenger.declare.api.sys.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/homepage")
public class CommunityAction extends BaseAction{

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 创建社区
	 * @throws Exception
	 */
	@RequestMapping("/createNewCommunity")
	@ResponseBody
	public boolean createNewCommunity(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String communityname = request.getParameter("communityname");
		return communityService.addCommunity(communityname, userid);
	}
	
	/**
	 * 添加社区
	 * @throws Exception
	 */
	@RequestMapping("/addCommunity")
	@ResponseBody
	public boolean addCommunity(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String communityid = request.getParameter("communityid");
		return userService.addUserCommunity(userid, communityid);
	}
	
	/**
	 * 删除社区
	 * @throws Exception
	 */
	@RequestMapping("/deleteCommunity")
	@ResponseBody
	public boolean deleteCommunity(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String communityid = request.getParameter("communityid");
		return userService.deleteUserCommunity(userid, communityid);
	}
	
	/**
	 * 推荐社区
	 * @throws Exception
	 */
	@RequestMapping("/getRecommendCommunities")
	@ResponseBody
	public void getRecommendCommunities(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    ArrayList<Community> recommendCommunities = communityService.getCommunityByName("hello");
	    JSONArray recommendCommunitiesJsonList = new JSONArray();
	    for (Community community : recommendCommunities) {
	    	JSONObject communityJson = new JSONObject();
	    	communityJson.put("communityid", community.getCommunityId());
	    	communityJson.put("communityname", community.getCommunityName());
	    	recommendCommunitiesJsonList.add(communityJson);
	    }
	    
	    OutputStream out = response.getOutputStream();
		out.write(recommendCommunitiesJsonList.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	/**
	 * 社区信息
	 * @throws Exception
	 */
	@RequestMapping("/getCommunityInformation")
	@ResponseBody
	public void getCommunityInformation(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    String communityname = request.getParameter("communityname");
	    ArrayList<Community> communityList = communityService.getCommunityByName(communityname);
	    JSONArray communityJsonList = new JSONArray();
	    
	    if (!communityList.isEmpty()) {
		for (Community community : communityList) {
		    JSONObject communityJson = new JSONObject();
		    communityJson.put("communityname", community.getCommunityName());
		    
		    JSONObject creatorJson = new JSONObject();
		    User creator = userService.getUserByUserId(community.getCommunityCreatorId());
		    creatorJson.put("creatorid", creator.getUserId());
		    creatorJson.put("creatorname", creator.getUserName());
		    communityJson.put("communitycreator", creatorJson);
		    
		    JSONArray memberJsonList = new JSONArray();
		    ArrayList<User> memberList = communityService.getUserByCommunity(community.getCommunityId());
		    if (memberList != null) {
			for (User user : memberList) {
			    JSONObject userJson = new JSONObject();
			    userJson.put("userid", user.getUserId());
			    userJson.put("username", user.getUserName());
			    memberJsonList.add(userJson);
			}
		    }
		    communityJson.put("communitymembers", memberJsonList);
		    communityJsonList.add(communityJson);
		}
	    }
	    
	    OutputStream out = response.getOutputStream();
	    out.write(communityJsonList.toString().getBytes("UTF-8"));
	    out.flush();
	}
}
