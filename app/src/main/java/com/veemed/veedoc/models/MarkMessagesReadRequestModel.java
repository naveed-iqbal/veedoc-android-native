package com.veemed.veedoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.List;

public class MarkMessagesReadRequestModel {
    @Expose
    @SerializedName("msgids")
    private List<Integer> msgids = null;

    public MarkMessagesReadRequestModel(Integer... paramVarArgs) {
        this.msgids = Arrays.asList(paramVarArgs);
    }

    public List<Integer> getMsgids() {
        return this.msgids;
    }

    public void setMsgids(List<Integer> paramList) {
        this.msgids = paramList;
    }
}

