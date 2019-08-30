package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockTestListResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("mock_test_categories")
    @Expose
    private List<MockTestList> mockTests = null;

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

    public List<MockTestList> getMockTests() {
        return mockTests;
    }

    public void setMockTests(List<MockTestList> mockTests) {
        this.mockTests = mockTests;
    }

}

