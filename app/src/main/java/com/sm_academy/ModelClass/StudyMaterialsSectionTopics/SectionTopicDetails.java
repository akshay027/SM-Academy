package com.sm_academy.ModelClass.StudyMaterialsSectionTopics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionTopicDetails {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("locked")
    @Expose
    private Boolean locked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }



}
