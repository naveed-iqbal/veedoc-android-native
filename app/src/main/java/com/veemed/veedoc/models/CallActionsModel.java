package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallActionsModel {

    @SerializedName("specialistRequestId")
    @Expose
    private Integer specialistRequestId;
    @SerializedName("performedAction")
    @Expose
    private String performedAction;
    @SerializedName("performedBy")
    @Expose
    private Integer performedBy;
    @SerializedName("comments")
    @Expose
    private String comments;

    public Integer getSpecialistRequestId() {
        return specialistRequestId;
    }

    public void setSpecialistRequestId(Integer specialistRequestId) {
        this.specialistRequestId = specialistRequestId;
    }

    public String getPerformedAction() {
        return performedAction;
    }

    public void setPerformedAction(String performedAction) {
        this.performedAction = performedAction;
    }

    public Integer getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(Integer performedBy) {
        this.performedBy = performedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}