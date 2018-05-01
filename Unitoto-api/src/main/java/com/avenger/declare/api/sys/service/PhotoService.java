package com.avenger.declare.api.sys.service;

import java.util.ArrayList;

import com.avenger.declare.api.sys.entity.Comment;
import com.avenger.declare.api.sys.entity.Label;
import com.avenger.declare.api.sys.entity.Multiphoto;
import com.avenger.declare.api.sys.entity.Photo;
import com.avenger.declare.api.sys.entity.User;

public interface PhotoService {
    
    ArrayList<Photo> getPhotoByUserId(String userId);
    
    ArrayList<Photo> getPhotoByLabelContext(String labelContext);
    
    ArrayList<Photo> getPhotoByUserList(ArrayList<User> users);
    
    ArrayList<Photo> getAlbumByFirstPhotoId(String firstPhotoId);
    
    ArrayList<Comment> getCommentByPhotoId(String photoId);
    
    ArrayList<Label> getLabelByPhotoId(String photoId);
    
    ArrayList<Photo> getPhotoBySite(String Llongitude, String Llatitude, String Rlongitude, String Rlatitude);
    
    int getPhotoLikeNum(String photoId);
    
    boolean addPhotoComment(Comment comment);
    
    boolean addPhotoLabel(String photoId, String labelContext);
    
    boolean addPhoto(Photo photo);
    
    boolean addAlbum(ArrayList<Photo> photos, Multiphoto multiphoto);
    
    boolean addPhotoLikeNum(String photoId);
    
    boolean deletePhoto(String photoId);
    
    boolean deleteAlbum(String firstPhotoId);
    
    Photo getPhotoByPhotoId(String photoId);
    
}
