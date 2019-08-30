package com.sm_academy.ModelClass.BatchTabDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Upcomingbatches {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("seats_left")
    @Expose
    private Integer seatsLeft;
    @SerializedName("start_date_end_date_in_range_in_readable_format")
    @Expose
    private String startDateEndDateInRangeInReadableFormat;
    @SerializedName("live_session_hours_display")
    @Expose
    private String liveSessionHoursDisplay;
    @SerializedName("already_enrolled")
    @Expose
    private Boolean alreadyEnrolled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStartDateEndDateInRangeInReadableFormat() {
        return startDateEndDateInRangeInReadableFormat;
    }

    public void setStartDateEndDateInRangeInReadableFormat(String startDateEndDateInRangeInReadableFormat) {
        this.startDateEndDateInRangeInReadableFormat = startDateEndDateInRangeInReadableFormat;
    }

    public String getLiveSessionHoursDisplay() {
        return liveSessionHoursDisplay;
    }

    public void setLiveSessionHoursDisplay(String liveSessionHoursDisplay) {
        this.liveSessionHoursDisplay = liveSessionHoursDisplay;
    }

    public Boolean getAlreadyEnrolled() {
        return alreadyEnrolled;
    }

    public void setAlreadyEnrolled(Boolean alreadyEnrolled) {
        this.alreadyEnrolled = alreadyEnrolled;
    }

}
