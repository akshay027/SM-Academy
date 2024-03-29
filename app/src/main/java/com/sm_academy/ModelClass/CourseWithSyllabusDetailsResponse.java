package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseWithSyllabusDetailsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("course")
    @Expose
    private CourseWithSyallabus course;

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

    public CourseWithSyallabus getCourse() {
        return course;
    }

    public void setCourse(CourseWithSyallabus course) {
        this.course = course;
    }

}
