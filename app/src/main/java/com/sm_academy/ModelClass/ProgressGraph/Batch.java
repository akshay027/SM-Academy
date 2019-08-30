package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Batch {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("batch_type")
    @Expose
    private String batchType;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("seats_left")
    @Expose
    private Integer seatsLeft;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("start_date_end_date_in_range")
    @Expose
    private String startDateEndDateInRange;
    @SerializedName("start_date_end_date_in_range_in_readable_format")
    @Expose
    private String startDateEndDateInRangeInReadableFormat;
    @SerializedName("live_session_hours")
    @Expose
    private Integer liveSessionHours;
    @SerializedName("live_session_hours_display")
    @Expose
    private String liveSessionHoursDisplay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(Integer seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDateEndDateInRange() {
        return startDateEndDateInRange;
    }

    public void setStartDateEndDateInRange(String startDateEndDateInRange) {
        this.startDateEndDateInRange = startDateEndDateInRange;
    }

    public String getStartDateEndDateInRangeInReadableFormat() {
        return startDateEndDateInRangeInReadableFormat;
    }

    public void setStartDateEndDateInRangeInReadableFormat(String startDateEndDateInRangeInReadableFormat) {
        this.startDateEndDateInRangeInReadableFormat = startDateEndDateInRangeInReadableFormat;
    }

    public Integer getLiveSessionHours() {
        return liveSessionHours;
    }

    public void setLiveSessionHours(Integer liveSessionHours) {
        this.liveSessionHours = liveSessionHours;
    }

    public String getLiveSessionHoursDisplay() {
        return liveSessionHoursDisplay;
    }

    public void setLiveSessionHoursDisplay(String liveSessionHoursDisplay) {
        this.liveSessionHoursDisplay = liveSessionHoursDisplay;
    }
}
