package com.veemed;

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

}