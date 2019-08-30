package com.sm_academy.ModelClass.LiveSession;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveSessionResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("upcoming_live_session")
    @Expose
    private LiveSessionDetails upcomingLiveSession;
    @SerializedName("completed_live_sessions")
    @Expose
    private List<CompletedSession> completedLiveSessions = null;
    @SerializedName("upcoming_live_sessions")
    @Expose
    private List<UpcomingSession> upcomingLiveSessions = null;

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

    public LiveSessionDetails getUpcomingLiveSession() {
        return upcomingLiveSession;
    }

    public void setUpcomingLiveSession(LiveSessionDetails upcomingLiveSession) {
        this.upcomingLiveSession = upcomingLiveSession;
    }

    public List<CompletedSession> getCompletedLiveSessions() {
        return completedLiveSessions;
    }

    public void setCompletedLiveSessions(List<CompletedSession> completedLiveSessions) {
        this.completedLiveSessions = completedLiveSessions;
    }

    public List<UpcomingSession> getUpcomingLiveSessions() {
        return upcomingLiveSessions;
    }

    public void setUpcomingLiveSessions(List<UpcomingSession> upcomingLiveSessions) {
        this.upcomingLiveSessions = upcomingLiveSessions;
    }

    @Override
    public String toString() {
        return "LiveSessionResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", upcomingLiveSession=" + upcomingLiveSession +
                ", completedLiveSessions=" + completedLiveSessions +
                ", upcomingLiveSessions=" + upcomingLiveSessions +
                '}';
    }
}
