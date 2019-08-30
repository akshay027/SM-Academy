package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Exalogic on 2/15/2018.
 */

public class SigninResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user")
    @Expose
    private SigninUser user;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public SigninUser getUser() {
        return user;
    }

    public void setUser(SigninUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SigninResponse{" +
                "status='" + status + '\'' +
                ", authToken='" + authToken + '\'' +
                ", userType='" + userType + '\'' +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
