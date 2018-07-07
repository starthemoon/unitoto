package com.avenger.declare.sys.service.impl;

import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenger.declare.api.sys.entity.Comment;
import com.avenger.declare.api.sys.entity.Label;
import com.avenger.declare.api.sys.entity.Multiphoto;
import com.avenger.declare.api.sys.entity.Photo;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.PhotoService;
import com.avenger.declare.sys.dao.mapper.CommentMapper;
import com.avenger.declare.sys.dao.mapper.LabelMapper;
import com.avenger.declare.sys.dao.mapper.MultiphotoMapper;
import com.avenger.declare.sys.dao.mapper.PhotoMapper;
import com.avenger.declare.util.IdGeneratorUtil;


@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	private PhotoMapper photoMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private LabelMapper labelMapper;
	
	@Autowired
	private MultiphotoMapper multiphotoMapper;
	
	private Comparator<Photo> photoComparator = new Comparator<Photo>() {
	    public int compare(Photo photo1, Photo photo2) {
		return photo1.compareTo(photo2);
	    }
	};
	
	private 	Comparator<Comment> commentComparator = new Comparator<Comment>() {
	    public int compare(Comment comment1, Comment comment2) {
		return comment1.compareTo(comment2);
	    }
	};

	public ArrayList<Photo> getPhotoByUserId(String userId) {
	    // TODO Auto-generated method stub
	    ArrayList<Photo> photos = photoMapper.getPhotoByUserId(userId);
	    if (photos == null) {
		return null;
	    } else {
		photos.sort(photoComparator);
		return photos;
	    }
	}

	public ArrayList<Photo> getPhotoByLabelContext(String labelContext) {
	    // TODO Auto-generated method stub
	    ArrayList<Photo> photos = null;
	    Label label = labelMapper.getLabelByLabelContext(labelContext);
	    String photo = null;
	    if (label != null) {
		photo = label.getLabelPhotoId();
	    }
	    if (photo == null) {
		return photos;
	    } else {
		photos = new ArrayList<Photo>();
		String temp = "";
		char[] ch = photo.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			Photo photoTemp = photoMapper.getPhotoById(temp);
			photos.add(photoTemp);
			temp = "";
		    }
		}
		photos.sort(photoComparator);
		return photos;
	    }
	}

	public ArrayList<Photo> getPhotoByUserList(ArrayList<User> users) {
	    // TODO Auto-generated method stub
	    ArrayList<Photo> photos = new ArrayList<Photo>();
	    if (users == null) {
		return photos;
	    }
	    for (User user : users) {
		ArrayList<Photo> photoTemp = getPhotoByUserId(user.getUserId());
		for (Photo photo : photoTemp) {
		    photos.add(photo);
		}
	    }
	    photos.sort(photoComparator);
	    return photos;
	}

	public ArrayList<Photo> getAlbumByFirstPhotoId(String firstPhotoId) {
	    // TODO Auto-generated method stub
	    ArrayList<Photo> photos = null;
	    Photo firstPhoto = photoMapper.getPhotoById(firstPhotoId);
	    String photo = multiphotoMapper.getMultiphotoByPhotoId(firstPhoto.getPhotoMultiphotoId());
	    if (photo == null) {
		return photos;
	    } else {
		photos = new ArrayList<Photo>();
		String temp = "";
		char[] ch = photo.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			Photo photoTemp = photoMapper.getPhotoById(temp);
			photos.add(photoTemp);
			temp = "";
		    }
		}
		photos.sort(photoComparator);
		return photos;
	    }
	}

	public ArrayList<Label> getLabelByPhotoId(String photoId) {
	    // TODO Auto-generated method stub
	    ArrayList<Label> labels = null;
	    String label = photoMapper.getLabelByPhotoId(photoId);
	    if (label == null) {
		return labels;
	    } else {
		labels = new ArrayList<Label>();
		String temp = "";
		char[] ch = label.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			Label labelTemp = labelMapper.getLabelByLabelId(temp);
			labels.add(labelTemp);
			temp = "";
		    }
		}
		return labels;
	    }
	}

	public boolean addPhoto(Photo photo) {
	    // TODO Auto-generated method stub
	    if (photo.getPhotoId() == null || photo.getPhotoAddress() == null || photo.getPhotoUploadTime() == null) {
		return false;
	    } else {
		photoMapper.insert(photo);
		return true;
	    }
	}

	public boolean addAlbum(ArrayList<Photo> photos, Multiphoto multiphoto) {
	    // TODO Auto-generated method stub
	    int c = 0;
	    String multiphotoPhotoId = "";
	    for (Photo photo : photos) {
		if (c == 0) {
		    if (photo.getPhotoId() == null || photo.getPhotoAddress() == null || photo.getPhotoUserId() == null ||
			    photo.getPhotoUploadTime() == null) {
			return false;
		    } else {
			multiphoto.setMultiphotoUserId(photo.getPhotoUserId());
			photo.setPhotoMultiphotoId(multiphoto.getMultiphotoId());
			photoMapper.insert(photo);
		    }
		} else {
		    if (photo.getPhotoId() == null || photo.getPhotoAddress() == null || photo.getPhotoUploadTime() == null) {
			return false;
		    } else {
			multiphotoPhotoId += photo.getPhotoId() + ";";
			photoMapper.insert(photo);
		    }
		}
		c++;
	    }
	    multiphoto.setMultiphotoPhotoId(multiphotoPhotoId);
	    multiphotoMapper.insert(multiphoto);
	    return true;
	}

	public boolean deletePhoto(String photoId) {
	    // TODO Auto-generated method stub
	    if (photoMapper.deleteByPrimaryKey(photoId) == 0) {
		return false;
	    } else {
		return true;
	    }
	}

	public boolean deleteAlbum(String firstPhotoId) {
	    // TODO Auto-generated method stub
	    Photo firstPhoto = photoMapper.getPhotoById(firstPhotoId);
	    ArrayList<Photo> photos = getAlbumByFirstPhotoId(firstPhotoId);
	    photos.add(firstPhoto);
	    String multiphotoId = firstPhoto.getPhotoMultiphotoId();
	    for (Photo photo : photos) {
		if (!deletePhoto(photo.getPhotoId())) {
		    return false;
		}
	    }
	    if (multiphotoMapper.deleteByPrimaryKey(multiphotoId) == 0) {
		return false;
	    } else {
		return true;
	    }
	}

	public boolean addPhotoComment(Comment comment) {
	    // TODO Auto-generated method stub
	    comment.setCommentId(IdGeneratorUtil.getId());
	    if (comment.getCommentId() != null && comment.getCommentContext() != null && comment.getCommentPhotoId() != null
		    && comment.getCommentUserId() != null && comment.getCommentUploadTime() != null) {
		commentMapper.insert(comment);
		return true;
	    } else {
		return false;
	    }
	}

	public boolean addPhotoLabel(String photoId, String labelContext) {
	    // TODO Auto-generated method stub
	    Photo photo = photoMapper.getPhotoById(photoId);
	    Label lableTemp = labelMapper.getLabelByLabelContext(labelContext);
	    String labels = photoMapper.getLabelByPhotoId(photo.getPhotoId());
	    String labelPhotoId = "";
	    String labelId = "";
	    if (lableTemp == null) {
		lableTemp = new Label();
		lableTemp.setLabelId(IdGeneratorUtil.getId());
		lableTemp.setLabelContext(labelContext);
		labelPhotoId = photo.getPhotoId() + ";";
		lableTemp.setLabelPhotoId(labelPhotoId);
		if (labels == null) {
		    labelId = lableTemp.getLabelId() + ";";
		    photo.setPhotoLabelId(labelId);
		} else {
		    labels += lableTemp.getLabelId() + ";";
		    photo.setPhotoLabelId(labels);
		}
		photoMapper.updatePhoto(photo);
		if (labelMapper.insert(lableTemp) == 0) {
		    return false;
		} else {
		    return true;
		}
	    } else {
		ArrayList<Photo> photos = getPhotoByLabelContext(lableTemp.getLabelContext());
		for (Photo p : photos) {
		    if (p.getPhotoId().equals(photoId)) {
			return false;
		    }
		}
		labelPhotoId = lableTemp.getLabelPhotoId();
		labelPhotoId += photo.getPhotoId() + ";";
		lableTemp.setLabelPhotoId(labelPhotoId);
		labelMapper.updateLabel(lableTemp);
		if (labels == null) {
		    labelId = lableTemp.getLabelId() + ";";
		    photo.setPhotoLabelId(labelId);
		} else {
		    labels += lableTemp.getLabelId() + ";";
		    photo.setPhotoLabelId(labels);
		}
		photoMapper.updatePhoto(photo);
		return true;
	    }
	}

	public ArrayList<Comment> getCommentByPhotoId(String photoId) {
	    // TODO Auto-generated method stub
	    ArrayList<Comment> comments = commentMapper.getCommentByPhotoId(photoId);
	    if (comments == null) {
		return null;
	    } else {
		comments.sort(commentComparator);
		return comments;
	    }
	}

	public int addPhotoLikeNum(String photoId) {
	    // TODO Auto-generated method stub
	    Photo photo = photoMapper.getPhotoById(photoId);
	    if (photo == null) {
		return 0;
	    } else {
		int likeNum = photo.getPhotoLikeNum();
		likeNum++;
		photo.setPhotoLikeNum(likeNum);
		photoMapper.updatePhoto(photo);
		return likeNum;
	    }
	}

	public int getPhotoLikeNum(String photoId) {
	    // TODO Auto-generated method stub
	    Photo photo = photoMapper.getPhotoById(photoId);
	    if (photo == null) {
		return 0;
	    } else {
		return photo.getPhotoLikeNum();
	    }
	}

	public Photo getPhotoByPhotoId(String photoId) {
	    // TODO Auto-generated method stub
	    return photoMapper.getPhotoById(photoId);
	}

	public ArrayList<Photo> getPhotoBySite(String Llongitude, String Llatitude, String Rlongitude, String Rlatitude) {
	    // TODO Auto-generated method stub
	    return photoMapper.getPhotoByPhotoSite(Llongitude, Llatitude, Rlongitude, Rlatitude);
	}

}
