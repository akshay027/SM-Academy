package com.sm_academy.ModelClass.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subscription {
    @SerializedName("batch_user_id")
    @Expose
    private Integer batchUserId;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("no_of_days")
    @Expose
    private Integer noOfDays;
    @SerializedName("total_no_of_days")
    @Expose
    private Integer totalNoOfDays;
    @SerializedName("extended_date")
    @Expose
    private String extendedDate;
    @SerializedName("extended")
    @Expose
    private Boolean extended;
    @SerializedName("course_extensions")
    @Expose
    private List<CourseExtension> courseExtensions = null;

    public Integer getBatchUserId() {
        return batchUserId;
    }

    public void setBatchUserId(Integer batchUserId) {
        this.batchUserId = batchUserId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Integer getTotalNoOfDays() {
        return totalNoOfDays;
    }

    public void setTotalNoOfDays(Integer totalNoOfDays) {
        this.totalNoOfDays = totalNoOfDays;
    }

    public String getExtendedDate() {
        return extendedDate;
    }

    public void setExtendedDate(String extendedDate) {
        this.extendedDate = extendedDate;
    }

    public Boolean getExtended() {
        return extended;
    }

    public void setExtended(Boolean extended) {
        this.extended = extended;
    }

    public List<CourseExtension> getCourseExtensions() {
        return courseExtensions;
    }

    public void setCourseExtensions(List<CourseExtension> courseExtensions) {
        this.courseExtensions = courseExtensions;
    }
}
