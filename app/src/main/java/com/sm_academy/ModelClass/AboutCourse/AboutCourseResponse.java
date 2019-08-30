package com.sm_academy.ModelClass.AboutCourse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutCourseResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("course_data")
    @Expose
    private CourseData courseData;

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

    public CourseData getCourseData() {
        return courseData;
    }

    public void setCourseData(CourseData courseData) {
        this.courseData = courseData;
    }

}
