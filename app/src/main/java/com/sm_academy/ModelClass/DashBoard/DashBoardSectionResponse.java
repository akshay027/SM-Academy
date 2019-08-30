package com.sm_academy.ModelClass.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardSectionResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sections")
    @Expose
    private List<DashBoardSections> sections = null;

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

    public List<DashBoardSections> getSections() {
        return sections;
    }

    public void setSections(List<DashBoardSections> sections) {
        this.sections = sections;
    }


}
