package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewMessageBody {

    @SerializedName("requestMsgSessionId")
    @Expose
    private Integer requestMsgSessionId;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getRequestMsgSessionId() {
        return requestMsgSessionId;
    }

    public void setRequestMsgSessionId(Integer requestMsgSessionId) {
        this.requestMsgSessionId = requestMsgSessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
