package com.avenger.declare.sys.dao.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.avenger.declare.api.sys.entity.User;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    String getFollowing(String userId);
    
    String getFollowed(String userId);
    
    void updateUser(User user);
    
    ArrayList<User> getUserByName(String userName);
    
    User getUserById(String userId);
}