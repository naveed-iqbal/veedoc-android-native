package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("requestMsgSessionId")
    @Expose
    private Integer requestMsgSessionId;
    @SerializedName("specialistName")
    @Expose
    private Object specialistName;
    @SerializedName("fromUserId")
    @Expose
    private Integer fromUserId;
    @SerializedName("isUnRead")
    @Expose
    private Boolean isUnRead;
    @SerializedName("receivedOnInUtc")
    @Expose
    private String receivedOnInUtc;
    @SerializedName("receivedOn")
    @Expose
    private String receivedOn;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("partnerSiteName")
    @Expose
    private String partnerSiteName;
    @SerializedName("isOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("specialistRequestId")
    @Expose
    private Integer specialistRequestId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestMsgSessionId() {
        return requestMsgSessionId;
    }

    public void setRequestMsgSessionId(Integer requestMsgSessionId) {
        this.requestMsgSessionId = requestMsgSessionId;
    }

    public Object getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(Object specialistName) {
        this.specialistName = specialistName;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Boolean getIsUnRead() {
        return isUnRead;
    }

    public void setIsUnRead(Boolean isUnRead) {
        this.isUnRead = isUnRead;
    }

    public String getReceivedOnInUtc() {
        return receivedOnInUtc;
    }

    public void setReceivedOnInUtc(String receivedOnInUtc) {
        this.receivedOnInUtc = receivedOnInUtc;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPartnerSiteName() {
        return partnerSiteName;
    }

    public void setPartnerSiteName(String partnerSiteName) {
        this.partnerSiteName = partnerSiteName;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getSpecialistRequestId() {
        return specialistRequestId;
    }

    public void setSpecialistRequestId(Integer specialistRequestId) {
        this.specialistRequestId = specialistRequestId;
    }

}
