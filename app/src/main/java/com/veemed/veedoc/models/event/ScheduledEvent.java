package com.veemed.veedoc.models.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduledEvent {

    @SerializedName("scheduleDate")
    @Expose
    private String scheduleDate;
    @SerializedName("details")
    @Expose
    private List<Details> details = null;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("facilityId")
    @Expose
    private Integer facilityId;
    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
    @SerializedName("inTimeZone")
    @Expose
    private Object inTimeZone;

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public Object getInTimeZone() {
        return inTimeZone;
    }

    public void setInTimeZone(Object inTimeZone) {
        this.inTimeZone = inTimeZone;
    }

}
