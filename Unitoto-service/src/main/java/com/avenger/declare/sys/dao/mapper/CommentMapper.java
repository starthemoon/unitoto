package com.avenger.declare.sys.dao.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.avenger.declare.api.sys.entity.Comment;

@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(Comment record);
    
    ArrayList<Comment> getCommentByPhotoId(String photoId);
    
    String getCommentUserByCommentId(String commentId);
}