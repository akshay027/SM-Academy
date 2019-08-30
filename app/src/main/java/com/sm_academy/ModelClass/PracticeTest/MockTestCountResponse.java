package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockTestCountResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mock_tests")
    @Expose
    private List<MockTestCount> mockTests = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MockTestCount> getMockTests() {
        return mockTests;
    }

    public void setMockTests(List<MockTestCount> mockTests) {
        this.mockTests = mockTests;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
