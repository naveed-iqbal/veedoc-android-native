package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PendingSession implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
    @SerializedName("specialityName")
    @Expose
    private String specialityName;
    @SerializedName("facilityId")
    @Expose
    private Integer facilityId;
    @SerializedName("facilityName")
    @Expose
    private String facilityName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pendingSince")
    @Expose
    private String pendingSince;
    @SerializedName("isHighPriority")
    @Expose
    private Boolean isHighPriority;
    @SerializedName("reasonForRequest")
    @Expose
    private String reasonForRequest;
    @SerializedName("durationInMinutes")
    @Expose
    private Integer durationInMinutes;
    @SerializedName("partnerSite")
    @Expose
    private Object partnerSite;
    @SerializedName("endPointId")
    @Expose
    private Integer endPointId;
    @SerializedName("endPoint")
    @Expose
    private String endPoint;
    @SerializedName("endPointName")
    @Expose
    private String endPointName;
    @SerializedName("receivedOn")
    @Expose
    private String receivedOn;
    @SerializedName("connectionFrom")
    @Expose
    private String connectionFrom;
    @SerializedName("isSigned")
    @Expose
    private Boolean isSigned;
    @SerializedName("mrn")
    @Expose
    private String mrn;
    @SerializedName("encounterNumber")
    @Expose
    private String encounterNumber;
    @SerializedName("patientName")
    @Expose
    private String patientName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPendingSince() {
        return pendingSince;
    }

    public void setPendingSince(String pendingSince) {
        this.pendingSince = pendingSince;
    }

    public Boolean getIsHighPriority() {
        return isHighPriority;
    }

    public void setIsHighPriority(Boolean isHighPriority) {
        this.isHighPriority = isHighPriority;
    }

    public String getReasonForRequest() {
        return reasonForRequest;
    }

    public void setReasonForRequest(String reasonForRequest) {
        this.reasonForRequest = reasonForRequest;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Object getPartnerSite() {
        return partnerSite;
    }

    public void setPartnerSite(Object partnerSite) {
        this.partnerSite = partnerSite;
    }

    public Integer getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(Integer endPointId) {
        this.endPointId = endPointId;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public String getConnectionFrom() {
        return connectionFrom;
    }

    public void setConnectionFrom(String connectionFrom) {
        this.connectionFrom = connectionFrom;
    }

    public Boolean getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Boolean isSigned) {
        this.isSigned = isSigned;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getEncounterNumber() {
        return encounterNumber;
    }

    public void setEncounterNumber(String encounterNumber) {
        this.encounterNumber = encounterNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

}