package com.avenger.declare.api.sys.entity;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 8056686552387039076L;

    private String userId;

    private String userPassword;

    private String userName;

    private String userFollowing;

    private String userFollowed;
    
    private String userPhone;
    
    private String userEmail;
    
    private String userDescription;
    
    private String userCommunity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId == null ? null : userId.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
	this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName == null ? null : userName.trim();
    }

    public String getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(String userFollowing) {
        this.userFollowing = userFollowing == null ? null : userFollowing.trim();
    }

    public String getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(String userFollowed) {
        this.userFollowed = userFollowed == null ? null : userFollowed.trim();
    }
    
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }
    
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }
    
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription == null ? null : userDescription.trim();
    }
    
    public String getUserCommunity() {
        return userCommunity;
    }

    public void setUserCommunity(String userCommunity) {
        this.userCommunity = userCommunity == null ? null : userCommunity.trim();
    }
}