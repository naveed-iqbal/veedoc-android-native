package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Endpoint {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("associatedUserId")
    @Expose
    private Integer associatedUserId;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("kartStatus")
    @Expose
    private String kartStatus;
    @SerializedName("statusUpdatedOn")
    @Expose
    private String statusUpdatedOn;
    @SerializedName("statusUpdatedOnInUtc")
    @Expose
    private String statusUpdatedOnInUtc;
    @SerializedName("cameraStatus")
    @Expose
    private String cameraStatus;
    @SerializedName("lastSessionTime")
    @Expose
    private String lastSessionTime;
    @SerializedName("lastSessionTimeInUtc")
    @Expose
    private String lastSessionTimeInUtc;
    @SerializedName("batteryLifeInSeconds")
    @Expose
    private Integer batteryLifeInSeconds;
    @SerializedName("partnerSiteName")
    @Expose
    private String partnerSiteName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("locationPhoneNumber")
    @Expose
    private String locationPhoneNumber;
    @SerializedName("isForDemo")
    @Expose
    private Boolean isForDemo;
    @SerializedName("facilityId")
    @Expose
    private Integer facilityId;
    @SerializedName("facilityName")
    @Expose
    private String facilityName;
    @SerializedName("partnerSiteId")
    @Expose
    private Integer partnerSiteId;
    @SerializedName("activationDate")
    @Expose
    private String activationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssociatedUserId() {
        return associatedUserId;
    }

    public void setAssociatedUserId(Integer associatedUserId) {
        this.associatedUserId = associatedUserId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getKartStatus() {
        return kartStatus;
    }

    public void setKartStatus(String kartStatus) {
        this.kartStatus = kartStatus;
    }

    public String getStatusUpdatedOn() {
        return statusUpdatedOn;
    }

    public void setStatusUpdatedOn(String statusUpdatedOn) {
        this.statusUpdatedOn = statusUpdatedOn;
    }

    public String getStatusUpdatedOnInUtc() {
        return statusUpdatedOnInUtc;
    }

    public void setStatusUpdatedOnInUtc(String statusUpdatedOnInUtc) {
        this.statusUpdatedOnInUtc = statusUpdatedOnInUtc;
    }

    public String getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(String cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public String getLastSessionTime() {
        return lastSessionTime;
    }

    public void setLastSessionTime(String lastSessionTime) {
        this.lastSessionTime = lastSessionTime;
    }

    public String getLastSessionTimeInUtc() {
        return lastSessionTimeInUtc;
    }

    public void setLastSessionTimeInUtc(String lastSessionTimeInUtc) {
        this.lastSessionTimeInUtc = lastSessionTimeInUtc;
    }

    public Integer getBatteryLifeInSeconds() {
        return batteryLifeInSeconds;
    }

    public void setBatteryLifeInSeconds(Integer batteryLifeInSeconds) {
        this.batteryLifeInSeconds = batteryLifeInSeconds;
    }

    public String getPartnerSiteName() {
        return partnerSiteName;
    }

    public void setPartnerSiteName(String partnerSiteName) {
        this.partnerSiteName = partnerSiteName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationPhoneNumber() {
        return locationPhoneNumber;
    }

    public void setLocationPhoneNumber(String locationPhoneNumber) {
        this.locationPhoneNumber = locationPhoneNumber;
    }

    public Boolean getIsForDemo() {
        return isForDemo;
    }

    public void setIsForDemo(Boolean isForDemo) {
        this.isForDemo = isForDemo;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Integer getPartnerSiteId() {
        return partnerSiteId;
    }

    public void setPartnerSiteId(Integer partnerSiteId) {
        this.partnerSiteId = partnerSiteId;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }


}
