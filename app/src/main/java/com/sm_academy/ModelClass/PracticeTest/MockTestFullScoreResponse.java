package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockTestFullScoreResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("score")
    @Expose
    private List<MockTestFullScore> score = null;

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

    public List<MockTestFullScore> getScore() {
        return score;
    }

    public void setScore(List<MockTestFullScore> score) {
        this.score = score;
    }
}
