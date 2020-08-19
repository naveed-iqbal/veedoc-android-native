package com.veemed.veedoc.models;

import androidx.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facility {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("timezoneDescription")
    @Expose
    private String timezoneDescription;
    @SerializedName("utcDSTOffSetInSeconds")
    @Expose
    private Integer utcDSTOffSetInSeconds;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("partnerSiteId")
    @Expose
    private Integer partnerSiteId;
    @SerializedName("tableauSiteId")
    @Expose
    private Integer tableauSiteId;
    @SerializedName("newAppointmentTime")
    @Expose
    private Integer newAppointmentTime;
    @SerializedName("followupAppointmentTime")
    @Expose
    private Integer followupAppointmentTime;
    @SerializedName("isInsurance")
    @Expose
    private Boolean isInsurance;
    @SerializedName("isCash")
    @Expose
    private Boolean isCash;
    @SerializedName("facilityGuid")
    @Expose
    private String facilityGuid;
    @SerializedName("newCashAmount")
    @Expose
    private Integer newCashAmount;
    @SerializedName("followupCashAmount")
    @Expose
    private Integer followupCashAmount;
    @SerializedName("onlineClinicLink")
    @Expose
    private String onlineClinicLink;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("facilitySpecialities")
    @Expose
    private List<FacilitySpeciality> facilitySpecialities = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTimezoneDescription() {
        return timezoneDescription;
    }

    public void setTimezoneDescription(String timezoneDescription) {
        this.timezoneDescription = timezoneDescription;
    }

    public Integer getUtcDSTOffSetInSeconds() {
        return utcDSTOffSetInSeconds;
    }

    public void setUtcDSTOffSetInSeconds(Integer utcDSTOffSetInSeconds) {
        this.utcDSTOffSetInSeconds = utcDSTOffSetInSeconds;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getPartnerSiteId() {
        return partnerSiteId;
    }

    public void setPartnerSiteId(Integer partnerSiteId) {
        this.partnerSiteId = partnerSiteId;
    }

    public Integer getTableauSiteId() {
        return tableauSiteId;
    }

    public void setTableauSiteId(Integer tableauSiteId) {
        this.tableauSiteId = tableauSiteId;
    }

    public Integer getNewAppointmentTime() {
        return newAppointmentTime;
    }

    public void setNewAppointmentTime(Integer newAppointmentTime) {
        this.newAppointmentTime = newAppointmentTime;
    }

    public Integer getFollowupAppointmentTime() {
        return followupAppointmentTime;
    }

    public void setFollowupAppointmentTime(Integer followupAppointmentTime) {
        this.followupAppointmentTime = followupAppointmentTime;
    }

    public Boolean getIsInsurance() {
        return isInsurance;
    }

    public void setIsInsurance(Boolean isInsurance) {
        this.isInsurance = isInsurance;
    }

    public Boolean getIsCash() {
        return isCash;
    }

    public void setIsCash(Boolean isCash) {
        this.isCash = isCash;
    }

    public String getFacilityGuid() {
        return facilityGuid;
    }

    public void setFacilityGuid(String facilityGuid) {
        this.facilityGuid = facilityGuid;
    }

    public Integer getNewCashAmount() {
        return newCashAmount;
    }

    public void setNewCashAmount(Integer newCashAmount) {
        this.newCashAmount = newCashAmount;
    }

    public Integer getFollowupCashAmount() {
        return followupCashAmount;
    }

    public void setFollowupCashAmount(Integer followupCashAmount) {
        this.followupCashAmount = followupCashAmount;
    }

    public String getOnlineClinicLink() {
        return onlineClinicLink;
    }

    public void setOnlineClinicLink(String onlineClinicLink) {
        this.onlineClinicLink = onlineClinicLink;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public List<FacilitySpeciality> getFacilitySpecialities() {
        return facilitySpecialities;
    }

    public void setFacilitySpecialities(List<FacilitySpeciality> facilitySpecialities) {
        this.facilitySpecialities = facilitySpecialities;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
