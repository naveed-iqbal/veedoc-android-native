package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndpointStatus {

    @SerializedName("endPointId")
    @Expose
    private Integer endPointId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(Integer endPointId) {
        this.endPointId = endPointId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}