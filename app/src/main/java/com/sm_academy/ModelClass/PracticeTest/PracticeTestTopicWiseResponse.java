package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PracticeTestTopicWiseResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tests")
    @Expose
    private List<PracticeTestTopicWise> practiceTests = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PracticeTestTopicWise> getPracticeTests() {
        return practiceTests;
    }

    public void setPracticeTests(List<PracticeTestTopicWise> practiceTests) {
        this.practiceTests = practiceTests;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
