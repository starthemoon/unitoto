package com.avenger.declare.sys.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenger.declare.api.sys.entity.Community;
import com.avenger.declare.api.sys.entity.User;
import com.avenger.declare.api.sys.service.UserService;
import com.avenger.declare.sys.dao.mapper.CommunityMapper;
import com.avenger.declare.sys.dao.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommunityMapper communityMapper;

	public boolean userLogin(String userId, String userPassword) {
	    // TODO Auto-generated method stub
	    User temp = userMapper.getUserById(userId);
	    if (temp == null) {
		return false;
	    } else {
		if (temp.getUserPassword().equals(userPassword)) {
		    return true;
		} else {
		    return false;
		}
	    }
	}

	public boolean addUser(User user) {
	    // TODO Auto-generated method stub
	    User temp = userMapper.getUserById(user.getUserId());
	    if (temp != null) {
		return false;
	    } else if (user.getUserId() == null || user.getUserName() == null || user.getUserPassword() == null) {
		return false;
	    } else {
		userMapper.insert(user);
		return true;
	    }
	}

	public boolean addUserFollowing(String originalUserId, String addUserId) {
	    // TODO Auto-generated method stub
	    User originalUser = userMapper.getUserById(originalUserId);
	    String following = originalUser.getUserFollowing();
	    if (following == null) {
		following = addUserId + ";";
		originalUser.setUserFollowing(following);
		userMapper.updateUser(originalUser);
	    } else {
		ArrayList<String> followings = new ArrayList<String>();
		String temp = "";
		char[] ch = following.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			followings.add(temp);
			temp = "";
		    }
		}
		if (followings.contains(addUserId)) {
		    return false;
		}
		following += addUserId + ";";
		originalUser.setUserFollowing(following);
		userMapper.updateUser(originalUser);
	    }
	    return true;
	}

	public boolean addUserFollowed(String originalUserId, String addUserId) {
	    // TODO Auto-generated method stub
	    User originalUser = userMapper.getUserById(originalUserId);
	    String followed = originalUser.getUserFollowed();
	    if (followed == null) {
		followed = addUserId + ";";
		originalUser.setUserFollowed(followed);
		userMapper.updateUser(originalUser);
	    } else {
		ArrayList<String> followeds = new ArrayList<String>();
		String temp = "";
		char[] ch = followed.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			followeds.add(temp);
			temp = "";
		    }
		}
		if (followeds.contains(addUserId)) {
		    return false;
		}
		followed += addUserId + ";";
		originalUser.setUserFollowed(followed);
		userMapper.updateUser(originalUser);
	    }
	    return true;
	}
	
	public boolean deleteUserFollowing(String originalUserId, String deleteUserId) {
	    // TODO Auto-generated method stub
	    ArrayList<User> users = getUserFollowing(originalUserId);
	    if (users == null) {
		return false;
	    } else {
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
		    User user = (User) iterator.next();
		    if (user.getUserId().equals(deleteUserId)) {
			iterator.remove();
		    }
		}
		String temp = "";
		for (User user : users) {
		    temp += user.getUserId() + ";";
		}
		User originalUser = userMapper.getUserById(originalUserId);
		originalUser.setUserFollowing(temp);
		userMapper.updateUser(originalUser);
		return true;
	    }
	}

	public boolean deleteUserFollowed(String originalUserId, String deleteUserId) {
	    // TODO Auto-generated method stub
	    ArrayList<User> users = getUserFollowed(originalUserId);
	    if (users == null) {
		return false;
	    } else {
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
		    User user = (User) iterator.next();
		    if (user.getUserId().equals(deleteUserId)) {
			iterator.remove();
		    }
		}
		String temp = "";
		for (User user : users) {
		    temp += user.getUserId() + ";";
		}
		User originalUser = userMapper.getUserById(originalUserId);
		originalUser.setUserFollowed(temp);
		userMapper.updateUser(originalUser);
		return true;
	    }
	}

	public ArrayList<User> getUserFollowing(String userId) {
	    // TODO Auto-generated method stub
	    ArrayList<User> users = null;
	    String following = userMapper.getFollowing(userId);
	    if (following == null) {
		return users;
	    } else {
		users = new ArrayList<User>();
		String temp = "";
		char[] ch = following.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			User userTemp = getUserByUserId(temp);
			users.add(userTemp);
			temp = "";
		    }
		}
		return users;
	    }
	}

	public ArrayList<User> getUserFollowed(String userId) {
	    // TODO Auto-generated method stub
	    ArrayList<User> users = null;
	    String followed = userMapper.getFollowed(userId);
	    if (followed == null) {
		return users;
	    } else {
		users = new ArrayList<User>();
		String temp = "";
		char[] ch = followed.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			User userTemp = getUserByUserId(temp);
			users.add(userTemp);
			temp = "";
		    }
		}
		return users;
	    }
	}

	public ArrayList<User> getUserByUserName(String userName) {
	    // TODO Auto-generated method stub
	    return userMapper.getUserByName(userName);
	}

	public User getUserByUserId(String userId) {
	    // TODO Auto-generated method stub
	    return userMapper.getUserById(userId);
	}

	public boolean addUserCommunity(String userId, String communityId) {
	    // TODO Auto-generated method stub
	    User user = userMapper.getUserById(userId);
	    String userCommunity = user.getUserCommunity();
	    
	    if (userCommunity == null) {
		userCommunity = communityId + ";";
		user.setUserCommunity(userCommunity);
		userMapper.updateUser(user);
	    } else {
		if (userCommunity.indexOf(communityId) != -1) {
		    return false;
		}
		userCommunity += communityId + ";";
		user.setUserCommunity(userCommunity);
		userMapper.updateUser(user);
	    }
	    
	    Community community = communityMapper.getCommunityById(communityId);
	    String communityUser = community.getCommunityMemberId();
	    if (communityUser == null) {
		communityUser = userId + ";";
		community.setCommunityMemberId(communityUser);
		communityMapper.updateCommunity(community);
	    } else {
		communityUser += userId + ";";
		community.setCommunityMemberId(communityUser);
		communityMapper.updateCommunity(community);
	    }
	    return true;
	}

	public boolean deleteUserCommunity(String userId, String communityId) {
	    // TODO Auto-generated method stub
	    User user = userMapper.getUserById(userId);
	    String userCommunity = user.getUserCommunity();
	    if (userCommunity == null) {
		return false;
	    } else {
		ArrayList<String> userCommunityTemp = new ArrayList<String>();
		String temp = "";
		char[] ch = userCommunity.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			userCommunityTemp.add(temp);
			temp = "";
		    }
		}
		temp = "";
		for (String string : userCommunityTemp) {
		    if (string.equals(communityId)) {
			continue;
		    }
		    temp += string + ";";
		}
		user.setUserCommunity(temp);
		userMapper.updateUser(user);
	    }
	    
	    Community community = communityMapper.getCommunityById(communityId);
	    String communityUser = community.getCommunityMemberId();
	    if (communityUser == null) {
		return false;
	    } else {
		ArrayList<String> communityUserTemp = new ArrayList<String>();
		String temp = "";
		char[] ch = communityUser.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			communityUserTemp.add(temp);
			temp = "";
		    }
		}
		temp = "";
		for (String string : communityUserTemp) {
		    if (string.equals(userId)) {
			continue;
		    }
		    temp += string + ";";
		}
		community.setCommunityMemberId(temp);
		communityMapper.updateCommunity(community);
	    }
	    return true;
	}

	public ArrayList<Community> getCommunityByUser(String userId) {
	    // TODO Auto-generated method stub
	    User user = userMapper.getUserById(userId);
	    String userCommunity = user.getUserCommunity();
	    ArrayList<Community> communities = null;
	    if (userCommunity == null) {
		return communities;
	    } else {
		communities = new ArrayList<Community>();
		String temp = "";
		char[] ch = userCommunity.toCharArray();
		for (char c : ch) {
		    if (c != ';') {
			temp += c;
		    } else {
			Community communityTemp = communityMapper.getCommunityById(temp);
			communities.add(communityTemp);
			temp = "";
		    }
		}
		return communities;
	    }
	}

	public boolean updateUserInfo(User user) {
	    // TODO Auto-generated method stub
	    userMapper.updateUser(user);
	    return true;
	}
}
