package com.sm_academy.ModelClass.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ielts {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("titleized_name")
    @Expose
    private String titleizedName;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitleizedName() {
        return titleizedName;
    }

    public void setTitleizedName(String titleizedName) {
        this.titleizedName = titleizedName;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

}

