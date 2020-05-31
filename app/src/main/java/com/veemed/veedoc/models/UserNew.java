package com.veemed.veedoc.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserNew {

    private Integer id;
    private Integer endPointId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String title;
    private String credentials;
    private String employer;
    private String address;
    private String address1;
    private String zipCode;
    private String secretAnswer1;
    private String secretAnswer2;
    private String secretQuestion1;
    private String secretQuestion2;
    private String userStatus;
    private Integer userStatusId;
    private String userGUID;
    private Integer createdBy;
    private Integer updatedBy;
    private String createdOn;
    private String updatedOn;
    private Boolean isActive;
    private Integer utcDSTOffsetInSeconds;
    private String timeZoneDescription;
    private String stateName;
    private Integer tated;
    private String cityName;
    private String countryName;
    private Integer countryId;
    private Integer systemId;
    private Integer facilityId;
    private Integer partnerSiteId;
    private Boolean isGroupMember;
    private Integer userGroupMemberId;
    private Boolean isSpecialist;

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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Integer userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getUserGUID() {
        return userGUID;
    }

    public void setUserGUID(String userGUID) {
        this.userGUID = userGUID;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
}
