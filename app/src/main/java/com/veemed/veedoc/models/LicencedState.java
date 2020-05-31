package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LicencedState {

    @SerializedName("specialistId")
    @Expose
    private Integer specialistId;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("licenceNumber")
    @Expose
    private Integer licenceNumber;

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(Integer licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

}