package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchTimingResponse {
    @SerializedName("live_sessions")
    @Expose
    private List<BatchTiming> liveSessions = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<BatchTiming> getLiveSessions() {
        return liveSessions;
    }

    public void setLiveSessions(List<BatchTiming> liveSessions) {
        this.liveSessions = liveSessions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
