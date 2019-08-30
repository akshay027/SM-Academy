package com.sm_academy.ModelClass.OngoingLiveSeesion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OngoingLiveSessionResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("live_session")
    @Expose
    private OngoingLiveSession liveSession;

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

    public OngoingLiveSession getLiveSession() {
        return liveSession;
    }

    public void setLiveSession(OngoingLiveSession liveSession) {
        this.liveSession = liveSession;
    }


}
