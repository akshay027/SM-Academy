package com.sm_academy.ModelClass.StudyMaterialsSectionTopics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionTopicResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("section_topics")
    @Expose
    private List<SectionTopicDetails> sectionTopics = null;
    @SerializedName("study_materials_count")
    @Expose
    private String study_materials_count;

    public String getStudy_materials_count() {
        return study_materials_count;
    }

    public void setStudy_materials_count(String study_materials_count) {
        this.study_materials_count = study_materials_count;
    }

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

    public List<SectionTopicDetails> getSectionTopics() {
        return sectionTopics;
    }

    public void setSectionTopics(List<SectionTopicDetails> sectionTopics) {
        this.sectionTopics = sectionTopics;
    }

}
