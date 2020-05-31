package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialistDetail {

    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
    @SerializedName("deaNumber")
    @Expose
    private String deaNumber;
    @SerializedName("npiNumber")
    @Expose
    private String npiNumber;
    @SerializedName("licencedStates")
    @Expose
    private List<LicencedState> licencedStates = null;
    @SerializedName("practiceGroup")
    @Expose
    private String practiceGroup;

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public String getDeaNumber() {
        return deaNumber;
    }

    public void setDeaNumber(String deaNumber) {
        this.deaNumber = deaNumber;
    }

    public String getNpiNumber() {
        return npiNumber;
    }

    public void setNpiNumber(String npiNumber) {
        this.npiNumber = npiNumber;
    }

    public List<LicencedState> getLicencedStates() {
        return licencedStates;
    }

    public void setLicencedStates(List<LicencedState> licencedStates) {
        this.licencedStates = licencedStates;
    }

    public String getPracticeGroup() {
        return practiceGroup;
    }

    public void setPracticeGroup(String practiceGroup) {
        this.practiceGroup = practiceGroup;
    }

}