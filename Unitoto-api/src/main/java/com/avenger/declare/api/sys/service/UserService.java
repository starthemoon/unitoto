package com.avenger.declare.api.sys.service;

import java.util.ArrayList;

import com.avenger.declare.api.sys.entity.Community;
import com.avenger.declare.api.sys.entity.User;

public interface UserService {
    
    boolean userLogin(String userId, String userPassword);
    
    boolean addUser(User user);
    
    boolean updateUserInfo(User user);
    
    boolean addUserFollowing(String originalUserId, String addUserId);
    
    boolean addUserFollowed(String originalUserId, String addUserId);
    
    boolean addUserCommunity(String userId, String communityId);
    
    boolean deleteUserFollowing(String originalUserId, String deleteUserId);
    
    boolean deleteUserFollowed(String originalUserId, String deleteUserId);
    
    boolean deleteUserCommunity(String userId, String communityId);
    
    ArrayList<User> getUserFollowing(String userId);
    
    ArrayList<User> getUserFollowed(String userId);
    
    ArrayList<User> getUserByUserName(String userName);
    
    ArrayList<Community> getCommunityByUser(String userId);
    
    User getUserByUserId(String userId);
}
