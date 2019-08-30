package com.sm_academy.ModelClass.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("learner")
    @Expose
    private Learner learner;
    @SerializedName("upcoming_session")
    @Expose
    private LiveSession upcomingSession;
    @SerializedName("courses")
    @Expose
    private List<DashCourse> courses = null;

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

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public LiveSession getUpcomingSession() {
        return upcomingSession;
    }

    public void setUpcomingSession(LiveSession upcomingSession) {
        this.upcomingSession = upcomingSession;
    }

    public List<DashCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<DashCourse> courses) {
        this.courses = courses;
    }

}