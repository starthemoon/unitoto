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
import com.avenger.declare.api.sys.entity.Label;
import com.avenger.declare.api.sys.entity.Photo;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.PhotoService;
import com.avenger.declare.api.sys.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/homepage")
public class SearchAction extends BaseAction{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping("/getUserByUserName")
	@ResponseBody
	public void getUserByUserName(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
		String userName = request.getParameter("username");
		ArrayList<User> users = userService.getUserByUserName(userName);
		JSONArray userJsonArray = new JSONArray();
		
		if (users != null) {
		    for (User user : users) {
			JSONObject userJson = new JSONObject();
			userJson.put("userid", user.getUserId());
			userJson.put("username", user.getUserName());
			userJson.put("userphone", user.getUserPhone());
			userJson.put("useremail", user.getUserEmail());
			userJson.put("userdescription", user.getUserDescription());
			userJsonArray.add(userJson);
		    }
		}
		
		OutputStream out = response.getOutputStream();
		out.write(userJsonArray.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	/**
	 * 返回用户信息
	 * @throws Exception
	 */
	@RequestMapping("/getUserInformation")
	@ResponseBody
	public void getUserInformation(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
		String userid = request.getParameter("userid");
		JSONObject userJson = new JSONObject();
		
		User user = userService.getUserByUserId(userid);
		
		if (user != null) {
			userJson.put("username", user.getUserName());
			userJson.put("userphone", user.getUserPhone());
			userJson.put("useremail", user.getUserEmail());
			userJson.put("userdescription", user.getUserDescription());
		}
		
		OutputStream out = response.getOutputStream();
		out.write(userJson.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	
	/**
	 * 通过标签获取图片
	 * @throws Exception
	 */
	@RequestMapping("/getPhotoByLabel")
	@ResponseBody
	JSONArray getPhotoByLabel(HttpServletRequest request) throws Exception{
		String photoLabelContext = request.getParameter("labelContext");
		ArrayList<Photo> photoList = photoService.getPhotoByLabelContext(photoLabelContext);
		JSONArray photoJsonList = new JSONArray();
		if (photoList != null) {
			for (Photo photo : photoList) {
				JSONObject photoJson = new JSONObject();
				photoJson.put("photoaddress", photo.getPhotoAddress());
				photoJson.put("photoid",photo.getPhotoId());
				photoJson.put("photouserid", photo.getPhotoUserId());
				photoJson.put("photousername",userService.getUserByUserId(photo.getPhotoUserId()).getUserName());
				photoJson.put("photouploadtime", photo.getPhotoUploadTime());
				photoJson.put("photolikenum", photo.getPhotoLikeNum());
				photoJson.put("photocontext", photo.getPhotoContext());
				
				ArrayList<Label> labelList = photoService.getLabelByPhotoId(photo.getPhotoId());
				JSONArray labelJsonList = new JSONArray();
				if (labelList != null) {
				    for (Label label : labelList) {
					JSONObject labelJson = new JSONObject();
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
		
	    return photoJsonList;
	}
}
