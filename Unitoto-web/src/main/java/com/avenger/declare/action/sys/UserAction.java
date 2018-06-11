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
import com.avenger.declare.api.sys.entity.Comment;
import com.avenger.declare.api.sys.entity.Community;
import com.avenger.declare.api.sys.entity.Label;
import com.avenger.declare.api.sys.entity.Photo;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.PhotoService;
import com.avenger.declare.api.sys.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/homepage")
public class UserAction extends BaseAction{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhotoService photoService;
	
	/**
	 * 注册
	 * @throws Exception
	 */
	@RequestMapping("/register")
	@ResponseBody
	public boolean register(HttpServletRequest request) throws Exception{
		User user = new User();
		String userid = request.getParameter("userid");
		String userpassword = request.getParameter("userpassword");
		String username = request.getParameter("username");
		user.setUserId(userid);
		user.setUserPassword(userpassword);
		user.setUserName(username);
		user.setUserFollowing(userid + ";");
	    return userService.addUser(user);
	}
	
	/**
	 * 登陆
	 * @throws Exception
	 */
	@RequestMapping("/login")
	@ResponseBody
	public boolean login(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String userpassword = request.getParameter("userpassword");
	    return userService.userLogin(userid, userpassword);
	}
	
	/**
	 * 更新用户信息
	 * @throws Exception
	 */
	@RequestMapping("/updatetUserInfomation")
	@ResponseBody
	public boolean updatetUserInfomation(HttpServletRequest request) throws Exception{
		String newusername = request.getParameter("username");
		String newuserphone = request.getParameter("userphone");
		String newuseremail = request.getParameter("useremail");
		String newuserdescription = request.getParameter("userdescription");
		String userid = request.getParameter("userid");
		User updated_user = userService.getUserByUserId(userid);
		updated_user.setUserName(newusername);
		updated_user.setUserPhone(newuserphone);
		updated_user.setUserEmail(newuseremail);
		updated_user.setUserDescription(newuserdescription);
	    return userService.updateUserInfo(updated_user);
	}
	
	/**
	 * 更新用户密码
	 * @throws Exception
	 */
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	public boolean updateUserPassword(HttpServletRequest request) throws Exception{
		String newpassword = request.getParameter("userpassword");
		String userid = request.getParameter("userid");
		User updated_user = userService.getUserByUserId(userid);
		updated_user.setUserPassword(newpassword);
		return userService.updateUserInfo(updated_user);
	}
	
	/**
	 * 用户的所有图片
	 * @throws Exception
	 */
	@RequestMapping("/getUserPhotos")
	@ResponseBody
	public void getUserPhotos(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
		String userid = request.getParameter("userid");
		JSONArray photoJsonList = new JSONArray();
		ArrayList<Photo> photoList = photoService.getPhotoByUserId(userid);
		
		if (photoList != null) {
        			for (Photo photo : photoList) {
        				JSONObject photoJson = new JSONObject();
        				photoJson.put("photoaddress", photo.getPhotoAddress());
        				photoJson.put("photoid",photo.getPhotoId());
        				photoJson.put("photouserid", photo.getPhotoUserId());
        				photoJson.put("photousername",userService.getUserByUserId(userid).getUserName());
        				photoJson.put("photouploadtime", photo.getPhotoUploadTime());
        				photoJson.put("photolikenum", photo.getPhotoLikeNum());
        				photoJson.put("photocontext", photo.getPhotoContext());
        				
        				ArrayList<Label> labelList = photoService.getLabelByPhotoId(photo.getPhotoId());
        				JSONArray labelJsonList = new JSONArray();
        				if (labelList != null) {
        				    for (Label label : labelList) {
        					JSONObject labelJson = new JSONObject();
        					labelJson.put("labelid", label.getLabelId());
        					labelJson.put("labelcontex", label.getLabelContext());
        					labelJsonList.add(labelJson);
        					}
        				}
        				photoJson.put("photolabels", labelJsonList);
        				
        				ArrayList<Comment> commentList = photoService.getCommentByPhotoId(photo.getPhotoId());
        				JSONArray commentJsonList = new JSONArray();
        				if (commentList != null) {
        				    for (Comment comment : commentList) {
        					JSONObject commentJson = new JSONObject();
        					commentJson.put("commentid", comment.getCommentId());
        					commentJson.put("commentuserid", comment.getCommentUserId());
        					commentJson.put("commentusername", userService.getUserByUserId(comment.getCommentUserId()));
        					commentJson.put("commentcontext", comment.getCommentContext());
        					commentJson.put("commentuploadtime", comment.getCommentUploadTime());
        					commentJsonList.add(commentJson);
        					}
        				}
        				photoJson.put("photocomments", commentJsonList);
        				
        				photoJsonList.add(photoJson);
        			}
		}
		
		OutputStream out = response.getOutputStream();
		out.write(photoJsonList.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	/**
	 * 用户参加的社区
	 * @throws Exception
	 */
	@RequestMapping("/getUserCommunities")
	@ResponseBody
	public void getUserCommunities(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    String userid = request.getParameter("userid");
	    ArrayList<Community> communityList = userService.getCommunityByUser(userid);
	    JSONArray communityJsonList = new JSONArray();
	    for (Community community : communityList) {
	    	JSONObject communityJson = new JSONObject();
	    	communityJson.put("communityid", community.getCommunityId());
	    	communityJson.put("communityname", community.getCommunityName());
	    	communityJsonList.add(communityJson);
	    }
	    
	    OutputStream out = response.getOutputStream();
		out.write(communityJsonList.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	/**
	 * 用户关注的人
	 * @throws Exception
	 */
	@RequestMapping("/getFollowings")
	@ResponseBody
	public void getFollowings(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    String userid = request.getParameter("userid");
	    ArrayList<User> userList = userService.getUserFollowing(userid);
	    JSONArray userJsonList = new JSONArray();
	    for (User user : userList) {
		if (user.getUserId().equals(userid)) {
		    continue;
		}
	    	JSONObject userJson = new JSONObject();
	    	userJson.put("userid", user.getUserId());
	    	userJson.put("username", user.getUserName());
	    	userJsonList.add(userJson);
	    }
	    
	    OutputStream out = response.getOutputStream();
		out.write(userJsonList.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	/**
	 * 关注用户的人
	 * @throws Exception
	 */
	@RequestMapping("/getFollowed")
	@ResponseBody
	public void getFollowed(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    String userid = request.getParameter("userid");
	    ArrayList<User> userList = userService.getUserFollowed(userid);
	    JSONArray userJsonList = new JSONArray();
	    for (User user : userList) {
	    	JSONObject userJson = new JSONObject();
	    	userJson.put("userid", user.getUserId());
	    	userJson.put("username", user.getUserName());
	    	userJsonList.add(userJson);
	    }
	    
	    OutputStream out = response.getOutputStream();
		out.write(userJsonList.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	/**
	 * 添加关注的用户
	 * @throws Exception
	 */
	@RequestMapping("/addFollow")
	@ResponseBody
	public boolean addFollowings(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String targetid = request.getParameter("targetid");
		return userService.addUserFollowing(userid, targetid) && userService.addUserFollowed(targetid, userid);
	}
	
	/**
	 * 删除关注的用户
	 * @throws Exception
	 */
	@RequestMapping("/deleteFollow")
	@ResponseBody
	public boolean deleteFollowings(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String targetid = request.getParameter("targetid");
		return userService.deleteUserFollowing(userid, targetid) && userService.deleteUserFollowed(targetid, userid);
	}
	
}
