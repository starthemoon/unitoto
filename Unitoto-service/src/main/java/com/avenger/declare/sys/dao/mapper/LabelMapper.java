package com.avenger.declare.sys.dao.mapper;

import org.springframework.stereotype.Repository;

import com.avenger.declare.api.sys.entity.Label;

@Repository
public interface LabelMapper {
    int deleteByPrimaryKey(String labelId);

    int insert(Label record);

    String getPhotoByLabelId(String labelId);
    
    Label getLabelByLabelId(String labelId);
    
    Label getLabelByLabelContext(String labelContext);
    
    void updateLabel(Label label);
}