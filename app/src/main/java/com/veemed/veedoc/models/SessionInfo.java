package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SessionInfo implements Serializable {

    @SerializedName("specialistRequestId")
    @Expose
    private Integer specialistRequestId;
    @SerializedName("resourceId")
    @Expose
    private String resourceId;
    @SerializedName("specialistFirstName")
    @Expose
    private String specialistFirstName;
    @SerializedName("specialistLastName")
    @Expose
    private String specialistLastName;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("facilityName")
    @Expose
    private String facilityName;
    @SerializedName("platform")
    @Expose
    private String platform;

    public Integer getSpecialistRequestId() {
        return specialistRequestId;
    }

    public void setSpecialistRequestId(Integer specialistRequestId) {
        this.specialistRequestId = specialistRequestId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSpecialistFirstName() {
        return specialistFirstName;
    }

    public void setSpecialistFirstName(String specialistFirstName) {
        this.specialistFirstName = specialistFirstName;
    }

    public String getSpecialistLastName() {
        return specialistLastName;
    }

    public void setSpecialistLastName(String specialistLastName) {
        this.specialistLastName = specialistLastName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}