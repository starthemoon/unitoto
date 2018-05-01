package com.avenger.declare.api.sys.service;

import java.util.ArrayList;

import com.avenger.declare.api.sys.entity.Community;
import com.avenger.declare.api.sys.entity.User;

public interface CommunityService {
    
    boolean addCommunity(String communityName, String communityCreatorId);
    
    ArrayList<Community> getCommunityByName(String communityName);
    
    ArrayList<User> getUserByCommunity(String communityId);
    
    Community getCommunityById(String communityId);
}
