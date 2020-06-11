package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectedCallPingModel {

    @SerializedName("specialistRequestId")
    @Expose
    private Integer specialistRequestId;
    @SerializedName("pingType")
    @Expose
    private String pingType;
    @SerializedName("pingClient")
    @Expose
    private String pingClient;
    @SerializedName("isPingBySpecialist")
    @Expose
    private String isPingBySpecialist;

    public Integer getSpecialistRequestId() {
        return specialistRequestId;
    }

    public void setSpecialistRequestId(Integer specialistRequestId) {
        this.specialistRequestId = specialistRequestId;
    }

    public String getPingType() {
        return pingType;
    }

    public void setPingType(String pingType) {
        this.pingType = pingType;
    }

    public String getPingClient() {
        return pingClient;
    }

    public void setPingClient(String pingClient) {
        this.pingClient = pingClient;
    }

    public String getIsPingBySpecialist() {
        return isPingBySpecialist;
    }

    public void setIsPingBySpecialist(String isPingBySpecialist) {
        this.isPingBySpecialist = isPingBySpecialist;
    }

}