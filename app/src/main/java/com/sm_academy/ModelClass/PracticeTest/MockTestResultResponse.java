package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockTestResultResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<MockResult> result = null;
    @SerializedName("mock_test_category_id")
    @Expose
    private String mock_test_category_id;

    public String getMock_test_category_id() {
        return mock_test_category_id;
    }

    public void setMock_test_category_id(String mock_test_category_id) {
        this.mock_test_category_id = mock_test_category_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<MockResult> getResult() {
        return result;
    }

    public void setResult(List<MockResult> result) {
        this.result = result;
    }


}
