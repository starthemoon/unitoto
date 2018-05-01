package com.avenger.declare.api.sys.entity;

import java.io.Serializable;

public class Multiphoto implements Serializable{
    private static final long serialVersionUID = -2220563205600608667L;

    private String multiphotoId;

    private String multiphotoUserId;

    private String multiphotoPhotoId;

    public String getMultiphotoId() {
        return multiphotoId;
    }

    public void setMultiphotoId(String multiphotoId) {
        this.multiphotoId = multiphotoId == null ? null : multiphotoId.trim();
    }

    public String getMultiphotoUserId() {
        return multiphotoUserId;
    }

    public void setMultiphotoUserId(String multiphotoUserId) {
        this.multiphotoUserId = multiphotoUserId == null ? null : multiphotoUserId.trim();
    }

    public String getMultiphotoPhotoId() {
        return multiphotoPhotoId;
    }

    public void setMultiphotoPhotoId(String multiphotoPhotoId) {
        this.multiphotoPhotoId = multiphotoPhotoId == null ? null : multiphotoPhotoId.trim();
    }
}