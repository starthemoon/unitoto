package com.avenger.declare.action.sys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.avenger.declare.action.base.BaseAction;
import com.avenger.declare.api.sys.entity.Comment;
import com.avenger.declare.api.sys.entity.Multiphoto;
import com.avenger.declare.api.sys.entity.Photo;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.PhotoService;
import com.avenger.declare.api.sys.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;;

@Controller
@RequestMapping("/homepage")
public class PhotoAction extends BaseAction{

	@Autowired
	private PhotoService photoService;
	@Autowired
	private UserService userService;

	@RequestMapping("/getImageUpflashing")
	@ResponseBody
	public JSONArray getImageUpflashing(HttpServletRequest request) throws Exception{
	    
	    /*
	     * get the userId in the request so that we can
	     * select the photoIds in the user table
	    */
	    String userId = request.getParameter("userId");
	    ArrayList<User> userFollowing = userService.getUserFollowing(userId);
	    ArrayList<Photo> photos = photoService.getPhotoByUserList(userFollowing);
	    
	    String photoId = request.getParameter("photoId");
	    JSONArray jsonArray = new JSONArray();
	    int count = 0;
	    boolean start = false;
	    /*
	     * find 3 photos with photoUploadTime is earlier
	     * than given photoUploadTime which is got by the photoId
	     */
	    for (Photo photo : photos) {
	    	if (count == 3) break;
	    	if (photo.getPhotoId().equals(photoId)) {
	    		start = true;
	    		continue;
	    	}
	    	if (start == false) continue;
	    	JSONObject jsonObject = new JSONObject();
	    	jsonObject.put("userId", photo.getPhotoUserId());
	    	jsonObject.put("userName", userService.getUserByUserId(photo.getPhotoUserId()).getUserName());
	    	jsonObject.put("photoId", photo.getPhotoId());
	    	jsonObject.put("photoAddress", photo.getPhotoAddress());
	    	jsonObject.put("photoContext", photo.getPhotoContext());
		int commentSize = photoService.getCommentByPhotoId(photo.getPhotoId()).size();
	    	jsonObject.put("commentSize", commentSize);
		if (commentSize == 0) {
		    jsonObject.put("commentContext", "");
		} else {
		    jsonObject.put("commentContext", photoService.getCommentByPhotoId(photo.getPhotoId()).get(0));
		}
		jsonObject.put("photoLikeNum", String.valueOf(photo.getPhotoLikeNum()));
		jsonObject.put("photoMultiphotoId", photo.getPhotoMultiphotoId());
	    	jsonArray.add(jsonObject);
	    	count++;
	    }
        return jsonArray;
	}

	@RequestMapping("/getImageDownflashing")
	@ResponseBody
	public JSONArray getImageDownflashing(HttpServletRequest request) throws Exception{
	    
	    /*
	     * get the userId in the request so that we can
	     * select the photoIds in the user table
	    */
	    String userId = request.getParameter("userId");
	    ArrayList<User> userFollowing = userService.getUserFollowing(userId);
	    ArrayList<Photo> photos = photoService.getPhotoByUserList(userFollowing);
	    
	    JSONArray jsonArray = new JSONArray();
	    int count = 0;
	    for (Photo photo : photos) {
		if (count == 3) break;
	    	JSONObject jsonObject = new JSONObject();
	    	// find all photos and give the first 3 photos
	    	jsonObject.put("userId", photo.getPhotoUserId());
	    	jsonObject.put("userName", userService.getUserByUserId(photo.getPhotoUserId()).getUserName());
	    	jsonObject.put("photoId", photo.getPhotoId());
	    	jsonObject.put("photoAddress", photo.getPhotoAddress());
	    	jsonObject.put("photoContext", photo.getPhotoContext());
		int commentSize = photoService.getCommentByPhotoId(photo.getPhotoId()).size();
	    	jsonObject.put("commentSize", commentSize);
		if (commentSize == 0) {
		    jsonObject.put("commentContext", "");
		} else {
		    jsonObject.put("commentContext", photoService.getCommentByPhotoId(photo.getPhotoId()).get(0));
		}
		jsonObject.put("photoLikeNum", String.valueOf(photo.getPhotoLikeNum()));
		jsonObject.put("photoMultiphotoId", photo.getPhotoMultiphotoId());
	    	jsonArray.add(jsonObject);
	    	count++;
	    }
        return jsonArray;
	}
	
	@RequestMapping("/getComment")
	@ResponseBody
	public JSONArray getComment(HttpServletRequest request) throws Exception{
	    /*
	     * get the photoId and return all comments about the photo
	    */
	    String photoId = request.getParameter("photoId");
	    ArrayList<Comment> comments = photoService.getCommentByPhotoId(photoId);
	    JSONArray jsonArray = new JSONArray();
	    for (Comment comment : comments) {
	    	JSONObject jsonObject = new JSONObject();
	    	jsonObject.put("userName", userService.getUserByUserId(comment.getCommentUserId()).getUserName());
	    	jsonObject.put("commentContext", comment.getCommentContext());
	    	jsonArray.add(jsonObject);
	    }

	    return jsonArray;
	}

	@RequestMapping("/uploadSingleImg")
	@ResponseBody
	public JSONObject uploadSingleImg(MultipartFile file, HttpServletRequest request, HttpSession session) throws Exception {
	    JSONObject jsonObject = new JSONObject();
	    String where = null;
	    if (file != null) {
		String path = null;
		String type = null;
		String fileName = file.getOriginalFilename();
		type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
		if (type != null) {
		    // get the userId
		    String userId = request.getParameter("userId");
		    if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase()) || "JPEG".equals(type.toUpperCase())) {
			// add the photo into database
			Photo photo = new Photo();
			
			// set the photoId
			String photoId = IdGeneratorUtil.getId();
			
			// set the photoUploadtime
			long currentTime = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date(currentTime);
			String photoUploadTime = formatter.format(date);
			
			// set the photoAddress
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String trueFileName = "photo/" + photoId + "." + type;
			path = realPath + "/" + trueFileName;

			// set a photoId and store the photo with the name of "photoId." followed by its type
			file.transferTo(new File(path));
			
			// set the photoContext of the cover of the album
			String photoContext = request.getParameter("photoContext");
			photo.setPhotoContext(photoContext);
			
			// add the photo into DB
			photo.setPhotoUserId(userId);
			photo.setPhotoId(photoId);
			photo.setPhotoUploadTime(photoUploadTime);
			photo.setPhotoAddress(trueFileName);
			photo.setPhotoLikeNum(0);
			photoService.addPhoto(photo);

			// photo recognition, get Label
			ArrayList<String> labelString = Tool.getLabelString(path);
			// addLabel
			if (labelString != null) {
			    for (String labelContext : labelString) {
				photoService.addPhotoLabel(photoId, labelContext);
			    }
			}
			
			where = trueFileName;
		    } else {
			jsonObject.put("Fail", "type must be one of 'GIF', 'PNG', 'JPG' or 'JPEG'");
			return jsonObject;
		    }
		} else {
		    jsonObject.put("Fail", "type is null!");
		    return jsonObject;
		}
	    } else {
		jsonObject.put("Fail", "file is null");
		return jsonObject;
	    }
	    jsonObject.put("Success", where);
	    return jsonObject;
	}

	@RequestMapping("/uploadPluralImg")
	@ResponseBody
	public JSONObject uploadPluralImg(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, HttpSession session) throws Exception {
	    JSONObject jsonObject = new JSONObject();
	    if (files != null) {
		// get the userId
		String userId = request.getParameter("userId");
		
		// get the multiphoto
		Multiphoto multiphoto = new Multiphoto();
		multiphoto.setMultiphotoId(IdGeneratorUtil.getId());
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		// set the labels for image recognition
		ArrayList<ArrayList<String> > labelContextArray = new ArrayList<ArrayList<String> >();
		
		for (int i = 0; i < files.length; i++) {
		    MultipartFile file = files[i];
		    if (file != null) {
			String path = null;
			String type = null;
			String fileName = file.getOriginalFilename();
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
			if (type != null) {
			    if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase()) || "JPEG".equals(type.toUpperCase())) {
				// add the photo into database
				Photo photo = new Photo();
				
				// set the photoId
				String photoId = IdGeneratorUtil.getId();
				
				// set the photoUploadtime
				long currentTime = System.currentTimeMillis();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date(currentTime);
				String photoUploadTime = formatter.format(date);
				
				// set the photoAddress
				String realPath = request.getSession().getServletContext().getRealPath("/");
				String trueFileName = "photo/" + photoId + "." + type;
				path = realPath + "/" + trueFileName;
				
				// set a photoId and store the photo with the name of "photoId." followed by its type
				file.transferTo(new File(path));

				// photo recognition, get Label
				ArrayList<String> labelString = Tool.getLabelString(path);
				labelContextArray.add(labelString);
				
				// set the photoContext of the cover of the album
				if (i == 0) {
				    String photoContext = request.getParameter("photoContext");
				    photo.setPhotoContext(photoContext);
				    photo.setPhotoUserId(userId);
				}
				
				// add the photo into DB
				photo.setPhotoId(photoId);
				photo.setPhotoUploadTime(photoUploadTime);
				photo.setPhotoAddress(trueFileName);
				photo.setPhotoLikeNum(0);
				// cause the code after the loop has done this, ignore it
				// photoService.addPhoto(photo);
				
				// add the photo into ArrayList
				photos.add(photo);
				
				// add the photo information into the jsonObject
				jsonObject.put("Success" + String.valueOf(i + 1), trueFileName);
			    } else {
				jsonObject.put("Fail" + String.valueOf(i + 1), "type must be one of 'GIF', 'PNG', 'JPG' or 'JPEG'");
			    }
			} else {
			    jsonObject.put("Fail" + String.valueOf(i + 1), "type is null!");
			}
		    } else {
			jsonObject.put("Fail" + String.valueOf(i + 1), "file is null");
		    }
		}
		// add the photos into multiphoto
		photoService.addAlbum(photos, multiphoto);
		
		// addLabel
		for (int i = 0; i < labelContextArray.size(); i++) {
		    ArrayList<String> labelContexts = labelContextArray.get(i);
		    if (labelContexts != null) {
			for (String labelContext : labelContexts) {
			    photoService.addPhotoLabel(photos.get(i).getPhotoId(), labelContext);
			}
		    }
		}
	    } else {
		jsonObject.put("Fail", "files is null");
	    }
	    return jsonObject;
	}
	
	@RequestMapping("/addLabel")
	@ResponseBody
	public boolean addLabel(HttpServletRequest request) {
	    String photoId = request.getParameter("photoId");
	    String labelContext = request.getParameter("labelContext");
	    if (photoId == null || labelContext == null) {
		return false;
	    } else {
		return photoService.addPhotoLabel(photoId, labelContext);
	    }
	}
	
	@RequestMapping("/addComment")
	@ResponseBody
	public boolean addComment(HttpServletRequest request) {
	    String commentPhotoId = request.getParameter("commentPhotoId");
	    String commentContext = request.getParameter("commentContext");
	    String commentUserId = request.getParameter("commentUserId");
	    long currentTime = System.currentTimeMillis();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date(currentTime);
	    String commentUploadTime = formatter.format(date);
	    Comment comment = new Comment();
	    comment.setCommentPhotoId(commentPhotoId);
	    comment.setCommentContext(commentContext);
	    comment.setCommentUserId(commentUserId);
	    comment.setCommentUploadTime(commentUploadTime);
	    return photoService.addPhotoComment(comment);
	}
	
	@RequestMapping("/getMultiphoto")
	@ResponseBody
	public JSONArray getMultiphoto(HttpServletRequest request) {
	    JSONArray jsonArray = new JSONArray();
	    
	    String photoId = request.getParameter("photoId");
	    ArrayList<Photo> photos = photoService.getAlbumByFirstPhotoId(photoId);
	    
	    Photo photo = photoService.getPhotoByPhotoId(photoId);
	    photos.add(0, photo);
	    for (int i = 0; i < photos.size(); i++) {
		photo = photos.get(i);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("photoId", photo.getPhotoId());
		jsonObject.put("photoAddress", photo.getPhotoAddress());
		if (i == 0) {
			jsonObject.put("photoContext", photo.getPhotoContext());
			int commentSize = photoService.getCommentByPhotoId(photo.getPhotoId()).size();
		    	jsonObject.put("commentSize", commentSize);
			if (commentSize == 0) {
			    jsonObject.put("commentContext", "");
			} else {
			    jsonObject.put("commentContext", photoService.getCommentByPhotoId(photo.getPhotoId()).get(0));
			}
			jsonObject.put("photoLikeNum", String.valueOf(photo.getPhotoLikeNum()));
		}
		
		jsonArray.add(jsonObject);
	    }
	    return jsonArray;
	}
}
