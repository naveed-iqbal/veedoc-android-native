package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserAPIRequest {

    @SerializedName("id")
    @Expose()
    private Integer id;

    @SerializedName("endPointId")
    @Expose()
    private Integer endPointId;

    @SerializedName("firstName")
    @Expose()
    private String firstName;

    @SerializedName("lastName")
    @Expose()
    private String lastName;

    @SerializedName("email")
    @Expose()
    private String email;

    @SerializedName("password")
    @Expose()
    private String password;

    @SerializedName("mobileNumber")
    @Expose()
    private String mobileNumber;

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("credentials")
    @Expose()
    private String credentials;

    @SerializedName("employer")
    @Expose()
    private String employer;

    @SerializedName("address")
    @Expose()
    private String address;

    @SerializedName("address1")
    @Expose()
    private String address1;

    @SerializedName("zipCode")
    @Expose()
    private String zipCode;

    @SerializedName("secretAnswer1")
    @Expose()
    private String secretAnswer1;

    @SerializedName("secretAnswer2")
    @Expose()
    private String secretAnswer2;

    @SerializedName("secretQuestion1")
    @Expose()
    private String secretQuestion1;

    @SerializedName("secretQuestion2")
    @Expose()
    private String secretQuestion2;

    @SerializedName("createdBy")
    @Expose()
    private Integer createdBy;


    @SerializedName("createdOn")
    @Expose()
    private String createdOn;


    @SerializedName("isActive")
    @Expose()
    private Boolean isActive;


    @SerializedName("timeZoneDescription")
    @Expose()
    private String timeZoneDescription;

    @SerializedName("stateName")
    @Expose()
    private String stateName;

    @SerializedName("tated")
    @Expose()
    private Integer tated;

    @SerializedName("cityName")
    @Expose()
    private String cityName;

    @SerializedName("countryName")
    @Expose()
    private String countryName;

    @SerializedName("countryId")
    @Expose()
    private Integer countryId;

    @SerializedName("systemId")
    @Expose()
    private Integer systemId;

    @SerializedName("facilityId")
    @Expose()
    private Integer facilityId;

    @SerializedName("partnerSiteId")
    @Expose()
    private Integer partnerSiteId;

    @SerializedName("isGroupMember")
    @Expose()
    private Boolean isGroupMember;

    @SerializedName("userGroupMemberId")
    @Expose()
    private Integer userGroupMemberId;

    @SerializedName("isSpecialist")
    @Expose()
    private Boolean isSpecialist;

    @SerializedName("specialist")
    @Expose()
    private SpecialistDetail specialist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(Integer endPointId) {
        this.endPointId = endPointId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
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

    public String getSecretAnswer1() {
        return secretAnswer1;
    }

    public void setSecretAnswer1(String secretAnswer1) {
        this.secretAnswer1 = secretAnswer1;
    }

    public String getSecretAnswer2() {
        return secretAnswer2;
    }

    public void setSecretAnswer2(String secretAnswer2) {
        this.secretAnswer2 = secretAnswer2;
    }

    public String getSecretQuestion1() {
        return secretQuestion1;
    }

    public void setSecretQuestion1(String secretQuestion1) {
        this.secretQuestion1 = secretQuestion1;
    }

    public String getSecretQuestion2() {
        return secretQuestion2;
    }

    public void setSecretQuestion2(String secretQuestion2) {
        this.secretQuestion2 = secretQuestion2;
    }



    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }


    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    public String getTimeZoneDescription() {
        return timeZoneDescription;
    }

    public void setTimeZoneDescription(String timeZoneDescription) {
        this.timeZoneDescription = timeZoneDescription;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getTated() {
        return tated;
    }

    public void setTated(Integer tated) {
        this.tated = tated;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Integer getPartnerSiteId() {
        return partnerSiteId;
    }

    public void setPartnerSiteId(Integer partnerSiteId) {
        this.partnerSiteId = partnerSiteId;
    }

    public Boolean getIsGroupMember() {
        return isGroupMember;
    }

    public void setIsGroupMember(Boolean isGroupMember) {
        this.isGroupMember = isGroupMember;
    }

    public Integer getUserGroupMemberId() {
        return userGroupMemberId;
    }

    public void setUserGroupMemberId(Integer userGroupMemberId) {
        this.userGroupMemberId = userGroupMemberId;
    }

    public Boolean getIsSpecialist() {
        return isSpecialist;
    }

    public void setIsSpecialist(Boolean isSpecialist) {
        this.isSpecialist = isSpecialist;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SpecialistDetail getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistDetail specialist) {
        this.specialist = specialist;
    }

    @Override
    public String toString() {
        return "UserAPIResponse{" +
                "id=" + id +
                ", endPointId=" + endPointId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", title='" + title + '\'' +
                ", credentials='" + credentials + '\'' +
                ", employer='" + employer + '\'' +
                ", address='" + address + '\'' +
                ", address1='" + address1 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", secretAnswer1='" + secretAnswer1 + '\'' +
                ", secretAnswer2='" + secretAnswer2 + '\'' +
                ", secretQuestion1='" + secretQuestion1 + '\'' +
                ", secretQuestion2='" + secretQuestion2 + '\'' +
                ", createdBy=" + createdBy +
                ", createdOn='" + createdOn + '\'' +
                ", isActive=" + isActive +
                ", timeZoneDescription='" + timeZoneDescription + '\'' +
                ", stateName='" + stateName + '\'' +
                ", tated=" + tated +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryId=" + countryId +
                ", systemId=" + systemId +
                ", facilityId=" + facilityId +
                ", partnerSiteId=" + partnerSiteId +
                ", isGroupMember=" + isGroupMember +
                ", userGroupMemberId=" + userGroupMemberId +
                ", isSpecialist=" + isSpecialist +
                '}';
    }

    public UserAPIRequest(Integer id, Integer endPointId, String firstName, String lastName, String email, String password, String mobileNumber, String title, String credentials, String employer, String address, String address1, String zipCode, String secretAnswer1, String secretAnswer2, String secretQuestion1, String secretQuestion2, Integer createdBy, String createdOn, Boolean isActive, String timeZoneDescription, String stateName, Integer tated, String cityName, String countryName, Integer countryId, Integer systemId, Integer facilityId, Integer partnerSiteId, Boolean isGroupMember, Integer userGroupMemberId, Boolean isSpecialist, SpecialistDetail specialist) {
        this.id = id;
        this.endPointId = endPointId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.title = title;
        this.credentials = credentials;
        this.employer = employer;
        this.address = address;
        this.address1 = address1;
        this.zipCode = zipCode;
        this.secretAnswer1 = secretAnswer1;
        this.secretAnswer2 = secretAnswer2;
        this.secretQuestion1 = secretQuestion1;
        this.secretQuestion2 = secretQuestion2;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.isActive = isActive;
        this.timeZoneDescription = timeZoneDescription;
        this.stateName = stateName;
        this.tated = tated;
        this.cityName = cityName;
        this.countryName = countryName;
        this.countryId = countryId;
        this.systemId = systemId;
        this.facilityId = facilityId;
        this.partnerSiteId = partnerSiteId;
        this.isGroupMember = isGroupMember;
        this.userGroupMemberId = userGroupMemberId;
        this.isSpecialist = isSpecialist;
        this.specialist = specialist;
    }
}
