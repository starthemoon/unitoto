package com.avenger.declare.sys.dao.mapper;

import org.springframework.stereotype.Repository;

import com.avenger.declare.api.sys.entity.Multiphoto;

@Repository
public interface MultiphotoMapper {
    int deleteByPrimaryKey(String multiphotoId);

    int insert(Multiphoto record);

    String getMultiphotoByPhotoId(String photoId);
    
    void updateMultiphoto(Multiphoto multiphoto);
}