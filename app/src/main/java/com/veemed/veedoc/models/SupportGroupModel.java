package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SupportGroupModel {
    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("isSupportGroup")
    private Boolean isSupportGroup;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("userGroupMembers")
    private List<UserAPIRequest> userGroupMembers = null;

    public Integer getId() {
        return this.id;
    }

    public Boolean getIsSupportGroup() {
        return this.isSupportGroup;
    }

    public String getName() {
        return this.name;
    }

    public List<UserAPIRequest> getUserGroupMembers() {
        return this.userGroupMembers;
    }

    public void setId(Integer paramInteger) {
        this.id = paramInteger;
    }

    public void setIsSupportGroup(Boolean paramBoolean) {
        this.isSupportGroup = paramBoolean;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setUserGroupMembers(List<UserAPIRequest> paramList) {
        this.userGroupMembers = paramList;
    }
}
