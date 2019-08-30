package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainCoursesDetailsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("courses")
    @Expose
    private List<MainCoursesDetails> courses = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MainCoursesDetails> getCourses() {
        return courses;
    }

    public void setCourses(List<MainCoursesDetails> courses) {
        this.courses = courses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MainCoursesDetailsResponse{" +
                "status=" + status +
                ", courses=" + courses +
                ", message='" + message + '\'' +
                '}';
    }
}
