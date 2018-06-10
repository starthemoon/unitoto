package com.avenger.declare.sys.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenger.declare.api.sys.entity.Community;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.CommunityService;
import com.avenger.declare.sys.dao.mapper.CommunityMapper;
import com.avenger.declare.sys.dao.mapper.UserMapper;
import com.avenger.declare.util.IdGeneratorUtil;

@Service
public class CommunityServiceImpl implements CommunityService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommunityMapper communityMapper;

	public boolean addCommunity(String communityName, String communityCreatorId) {
	    // TODO Auto-generated method stub
	    Community community = new Community();
	    community.setCommunityId(IdGeneratorUtil.getId());
	    community.setCommunityName(communityName);
	    community.setCommunityCreatorId(communityCreatorId);
	    if (communityMapper.insert(community) == 0) {
		return false;
	    } else {
		User user = userMapper.getUserById(communityCreatorId);
		String userCommunity = user.getUserCommunity();
		if (userCommunity == null) {
		    userCommunity = community.getCommunityId() + ";";
		} else {
		    userCommunity += community.getCommunityId() + ";";
		}
		user.setUserCommunity(userCommunity);
		userMapper.updateUser(user);
		return true;
	    }
	}

	public ArrayList<Community> getCommunityByName(String communityName) {
	    // TODO Auto-generated method stub
	    return communityMapper.getCommunityByName(communityName);
	}

	public ArrayList<User> getUserByCommunity(String communityId) {
	    // TODO Auto-generated method stub
	    Community community = communityMapper.getCommunityById(communityId);
	    String communityUser = community.getCommunityMemberId();
	    ArrayList<User> users = null;
	    if (communityUser == null) {
		return users;
	    } else {
		users = new ArrayList<User>();
		String temp = "";
		char[] ch = communityUser.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			User user = userMapper.getUserById(temp);
			users.add(user);
			temp = "";
		    }
		}
		return users;
	    }
	}

	public Community getCommunityById(String communityId) {
	    // TODO Auto-generated method stub
	    return communityMapper.getCommunityById(communityId);
	}
}
