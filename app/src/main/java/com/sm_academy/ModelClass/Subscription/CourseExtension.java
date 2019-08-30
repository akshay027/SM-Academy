package com.sm_academy.ModelClass.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseExtension {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("number_of_days")
    @Expose
    private Integer numberOfDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

}
