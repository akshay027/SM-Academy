package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sections")
    @Expose
    private List<GraphSection> sections = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GraphSection> getSections() {
        return sections;
    }

    public void setSections(List<GraphSection> sections) {
        this.sections = sections;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

