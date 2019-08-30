package com.sm_academy.ModelClass.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sm_academy.ModelClass.SigninUser;

public class ProfileResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("learner")
    @Expose
    private SigninUser learner;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SigninUser getLearner() {
        return learner;
    }

    public void setLearner(SigninUser learner) {
        this.learner = learner;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", learner=" + learner +
                '}';
    }
}
