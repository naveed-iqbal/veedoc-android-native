package com.veemed.veedoc.webservices;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("access_token")
    String access_token;

    @SerializedName("expires_in")
    int expires_in;

    @SerializedName("token_type")
    String token_type;

    @SerializedName("refresh_token")
    String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
