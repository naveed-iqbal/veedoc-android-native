package com.veemed.veedoc.models.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("facilityName")
    @Expose
    private String facilityName;
    @SerializedName("specialistId")
    @Expose
    private Integer specialistId;
    @SerializedName("specialistName")
    @Expose
    private String specialistName;
    @SerializedName("spcialistContactNumber")
    @Expose
    private String spcialistContactNumber;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("shiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("shiftStartTime")
    @Expose
    private String shiftStartTime;
    @SerializedName("shiftEndTime")
    @Expose
    private String shiftEndTime;
    @SerializedName("shiftStartTimeInUTC")
    @Expose
    private String shiftStartTimeInUTC;
    @SerializedName("shiftEndTimeInUTC")
    @Expose
    private String shiftEndTimeInUTC;
    @SerializedName("utcDSTOffsetInSeconds")
    @Expose
    private Integer utcDSTOffsetInSeconds;
    @SerializedName("timeZoneDescription")
    @Expose
    private String timeZoneDescription;
    @SerializedName("partnerSiteName")
    @Expose
    private String partnerSiteName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSpcialistContactNumber() {
        return spcialistContactNumber;
    }

    public void setSpcialistContactNumber(String spcialistContactNumber) {
        this.spcialistContactNumber = spcialistContactNumber;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(String shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    public String getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(String shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public String getShiftStartTimeInUTC() {
        return shiftStartTimeInUTC;
    }

    public void setShiftStartTimeInUTC(String shiftStartTimeInUTC) {
        this.shiftStartTimeInUTC = shiftStartTimeInUTC;
    }

    public String getShiftEndTimeInUTC() {
        return shiftEndTimeInUTC;
    }

    public void setShiftEndTimeInUTC(String shiftEndTimeInUTC) {
        this.shiftEndTimeInUTC = shiftEndTimeInUTC;
    }

    public Integer getUtcDSTOffsetInSeconds() {
        return utcDSTOffsetInSeconds;
    }

    public void setUtcDSTOffsetInSeconds(Integer utcDSTOffsetInSeconds) {
        this.utcDSTOffsetInSeconds = utcDSTOffsetInSeconds;
    }

    public String getTimeZoneDescription() {
        return timeZoneDescription;
    }

    public void setTimeZoneDescription(String timeZoneDescription) {
        this.timeZoneDescription = timeZoneDescription;
    }

    public String getPartnerSiteName() {
        return partnerSiteName;
    }

    public void setPartnerSiteName(String partnerSiteName) {
        this.partnerSiteName = partnerSiteName;
    }

}