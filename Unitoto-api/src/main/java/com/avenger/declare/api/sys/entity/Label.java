package com.avenger.declare.api.sys.entity;

import java.io.Serializable;

public class Label implements Serializable{
    private static final long serialVersionUID = 5924954742308272060L;

    private String labelId;

    private String labelPhotoId;

    private String labelContext;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId == null ? null : labelId.trim();
    }

    public String getLabelPhotoId() {
        return labelPhotoId;
    }

    public void setLabelPhotoId(String labelPhotoId) {
        this.labelPhotoId = labelPhotoId == null ? null : labelPhotoId.trim();
    }

    public String getLabelContext() {
        return labelContext;
    }

    public void setLabelContext(String labelContext) {
        this.labelContext = labelContext == null ? null : labelContext.trim();
    }
}