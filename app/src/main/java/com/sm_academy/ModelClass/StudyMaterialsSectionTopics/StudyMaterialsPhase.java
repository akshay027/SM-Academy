package com.sm_academy.ModelClass.StudyMaterialsSectionTopics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyMaterialsPhase {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("section_topic_id")
    @Expose
    private String sectionTopicId;
    @SerializedName("section_topic_topic")
    @Expose
    private String sectionTopicTopic;
    @SerializedName("locked")
    @Expose
    private Boolean locked;

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

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionTopicId() {
        return sectionTopicId;
    }

    public void setSectionTopicId(String sectionTopicId) {
        this.sectionTopicId = sectionTopicId;
    }

    public String getSectionTopicTopic() {
        return sectionTopicTopic;
    }

    public void setSectionTopicTopic(String sectionTopicTopic) {
        this.sectionTopicTopic = sectionTopicTopic;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

}
