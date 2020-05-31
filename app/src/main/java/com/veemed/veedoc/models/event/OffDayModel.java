package com.veemed.veedoc.models.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OffDayModel {

    @SerializedName("offDay")
    @Expose
    private String offDay;
    @SerializedName("isMarked")
    @Expose
    private Boolean isMarked;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getOffDay() {
        return offDay;
    }

    public void setOffDay(String offDay) {
        this.offDay = offDay;
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(Boolean isMarked) {
        this.isMarked = isMarked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}