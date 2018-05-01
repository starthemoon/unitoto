package com.avenger.declare.api.sys.entity;

import java.io.Serializable;

public class Community implements Serializable{
    private static final long serialVersionUID = 970864237419466995L;

    private String communityId;

    private String communityName;

    private String communityCreatorId;

    private String communityMemberId;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId == null ? null : communityId.trim();
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName == null ? null : communityName.trim();
    }

    public String getCommunityCreatorId() {
        return communityCreatorId;
    }

    public void setCommunityCreatorId(String communityCreatorId) {
        this.communityCreatorId = communityCreatorId == null ? null : communityCreatorId.trim();
    }

    public String getCommunityMemberId() {
        return communityMemberId;
    }

    public void setCommunityMemberId(String communityMemberId) {
        this.communityMemberId = communityMemberId == null ? null : communityMemberId.trim();
    }
}