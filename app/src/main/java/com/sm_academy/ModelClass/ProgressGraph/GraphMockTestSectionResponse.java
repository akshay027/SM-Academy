package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphMockTestSectionResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sections")
    @Expose
    private GraphMockTestSection sections;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GraphMockTestSection getSections() {
        return sections;
    }

    public void setSections(GraphMockTestSection sections) {
        this.sections = sections;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
