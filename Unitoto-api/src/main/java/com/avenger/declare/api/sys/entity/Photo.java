package com.avenger.declare.api.sys.entity;

import java.io.Serializable;

public class Photo implements Comparable<Object>, Serializable{
    private static final long serialVersionUID = -4196464392435224917L;

    private String photoId;

    private String photoAddress;

    private String photoLongitude;

    private String photoLatitude;

    private String photoUserId;

    private String photoUploadTime;

    private String photoLabelId;

    private String photoContext;

    private String photoMultiphotoId;
    
    private int photoLikeNum;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId == null ? null : photoId.trim();
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress == null ? null : photoAddress.trim();
    }

    public String getPhotoLongitude() {
        return photoLongitude;
    }

    public void setPhotoLongitude(String photoLongitude) {
        this.photoLongitude = photoLongitude == null ? null : photoLongitude.trim();
    }

    public String getPhotoLatitude() {
        return photoLatitude;
    }

    public void setPhotoLatitude(String photoLatitude) {
        this.photoLatitude = photoLatitude == null ? null : photoLatitude.trim();
    }

    public String getPhotoUserId() {
        return photoUserId;
    }

    public void setPhotoUserId(String photoUserId) {
        this.photoUserId = photoUserId == null ? null : photoUserId.trim();
    }

    public String getPhotoUploadTime() {
        return photoUploadTime;
    }

    public void setPhotoUploadTime(String photoUploadTime) {
        this.photoUploadTime = photoUploadTime == null ? null : photoUploadTime.trim();
    }

    public String getPhotoLabelId() {
        return photoLabelId;
    }

    public void setPhotoLabelId(String photoLabelId) {
        this.photoLabelId = photoLabelId == null ? null : photoLabelId.trim();
    }

    public String getPhotoContext() {
        return photoContext;
    }

    public void setPhotoContext(String photoContext) {
        this.photoContext = photoContext == null ? null : photoContext.trim();
    }

    public String getPhotoMultiphotoId() {
        return photoMultiphotoId;
    }

    public void setPhotoMultiphotoId(String photoMultiphotoId) {
        this.photoMultiphotoId = photoMultiphotoId == null ? null : photoMultiphotoId.trim();
    }
    
    public int getPhotoLikeNum() {
	return photoLikeNum;
    }
    
    public void setPhotoLikeNum(int photoLikeNum) {
	this.photoLikeNum = photoLikeNum;
    }

    @Override
    public int compareTo(Object o) {
	// TODO Auto-generated method stub
	Photo photo = (Photo) o;
	if (this.photoUploadTime.compareTo(photo.photoUploadTime) < 0) {
	    return 1;
	} else if (this.photoUploadTime.compareTo(photo.photoUploadTime) > 0) {
	    return -1;
	} else {
	    return 0;
	}
    }
}