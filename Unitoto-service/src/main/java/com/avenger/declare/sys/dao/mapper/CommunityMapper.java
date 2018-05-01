package com.avenger.declare.sys.dao.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.avenger.declare.api.sys.entity.Community;

@Repository
public interface CommunityMapper {

    int deleteByPrimaryKey(String communityId);

    int insert(Community record);
    
    String getUserByCommunityId(String communityId);
    
    Community getCommunityById(String communityId);
    
    ArrayList<Community> getCommunityByName(String communityName);
    
    void updateCommunity(Community community);
}