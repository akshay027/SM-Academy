package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseSummariesResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sections")
    @Expose
    private List<CourseSummaries> sections = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CourseSummaries> getSections() {
        return sections;
    }

    public void setSections(List<CourseSummaries> sections) {
        this.sections = sections;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
