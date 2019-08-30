package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreacticeTestTopicsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("section_topics")
    @Expose
    private List<PreacticeTestSectionTopic> sectionTopics = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PreacticeTestSectionTopic> getSectionTopics() {
        return sectionTopics;
    }

    public void setSectionTopics(List<PreacticeTestSectionTopic> sectionTopics) {
        this.sectionTopics = sectionTopics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
