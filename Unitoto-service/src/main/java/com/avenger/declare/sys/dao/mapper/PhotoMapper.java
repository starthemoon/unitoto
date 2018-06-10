package com.avenger.declare.sys.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.avenger.declare.api.sys.entity.Photo;

@Repository
public interface PhotoMapper {
    int deleteByPrimaryKey(String photoId);

    int insert(Photo record);

    ArrayList<Photo> getPhotoByUserId(String userId);
    
    ArrayList<Photo> getPhotoByPhotoSite(@Param("Llongitude")String Llongitude, @Param("Llatitude")String Llatitude,
	    @Param("Rlongitude")String Rlongitude, @Param("Rlatitude")String Rlatitude);
    
    Photo getPhotoById(String photoId);
    
    String getLabelByPhotoId(String photoId);
    
    void updatePhoto(Photo photo);
}