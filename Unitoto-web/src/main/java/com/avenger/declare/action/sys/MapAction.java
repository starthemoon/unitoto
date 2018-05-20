package com.avenger.declare.action.sys;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avenger.declare.action.base.BaseAction;
import com.avenger.declare.api.sys.entity.Photo;
import com.avenger.declare.api.sys.service.PhotoService;
import com.avenger.declare.api.sys.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/homepage")
public class MapAction extends BaseAction{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping("/getPhotoBySite")
	@ResponseBody
  	JSONArray getPhotoBySite(HttpServletRequest request) {
		String Llatitude = "23.0783104602";
		String Llongitude = "113.3498954773";
		String Rlatitude = "23.0320309737";
		String Rlongitude = "113.4182167053";
		ArrayList<Photo> photoList = photoService.getPhotoBySite(Llongitude, Llatitude, Rlongitude, Rlatitude);
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
				photoJson.put("photoLongitude", photo.getPhotoLongitude());
				photoJson.put("photoLatitude", photo.getPhotoLatitude());
				
				/*
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
					commentJson.put("commentid", comment.getCommentId());
					commentJson.put("commentuserid", comment.getCommentUserId());
					commentJson.put("commentusername", userService.getUserByUserId(comment.getCommentUserId()));
					commentJson.put("commentcontext", comment.getCommentContext());
					commentJson.put("commentuploadtime", comment.getCommentUploadTime());
					commentJsonList.add(commentJson);
					}
				}
				photoJson.put("photocomments", commentJsonList);
				*/
				
				photoJsonList.add(photoJson);
			}
		}
	    return photoJsonList;
	}
}
