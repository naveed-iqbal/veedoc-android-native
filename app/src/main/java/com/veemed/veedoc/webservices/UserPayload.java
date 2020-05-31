package com.veemed.veedoc.webservices;

public class UserPayload {
    private String client_id;
    private String client_secret;
    private String username;
    private String password;
    private String grant_type;

    public UserPayload(String client_id, String client_secret, String username, String password, String grant_type) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
