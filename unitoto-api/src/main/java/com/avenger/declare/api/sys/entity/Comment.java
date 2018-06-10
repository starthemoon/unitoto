package com.avenger.declare.api.sys.entity;

import java.io.Serializable;

public class Comment implements Comparable<Object>, Serializable{
    private static final long serialVersionUID = -3555668670967507535L;

    private String commentId;

    private String commentContext;

    private String commentPhotoId;

    private String commentUserId;

    private String commentUploadTime;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getCommentContext() {
        return commentContext;
    }

    public void setCommentContext(String commentContext) {
        this.commentContext = commentContext == null ? null : commentContext.trim();
    }

    public String getCommentPhotoId() {
        return commentPhotoId;
    }

    public void setCommentPhotoId(String commentPhotoId) {
        this.commentPhotoId = commentPhotoId == null ? null : commentPhotoId.trim();
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId == null ? null : commentUserId.trim();
    }

    public String getCommentUploadTime() {
        return commentUploadTime;
    }

    public void setCommentUploadTime(String commentUploadTime) {
        this.commentUploadTime = commentUploadTime == null ? null : commentUploadTime.trim();
    }

    @Override
    public int compareTo(Object o) {
	// TODO Auto-generated method stub
	Comment comment = (Comment) o;
	if (this.commentUploadTime.compareTo(comment.commentUploadTime) < 0) {
	    return 1;
	} else if (this.commentUploadTime.compareTo(comment.commentUploadTime) > 0) {
	    return -1;
	} else {
	    return 0;
	}
    }
}