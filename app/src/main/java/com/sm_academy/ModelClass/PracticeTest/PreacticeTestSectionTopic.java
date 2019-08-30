package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreacticeTestSectionTopic {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("no_of_tests")
    @Expose
    private Integer noOfTests;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("locked")
    @Expose
    private Boolean locked;

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getNoOfTests() {
        return noOfTests;
    }

    public void setNoOfTests(Integer noOfTests) {
        this.noOfTests = noOfTests;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

}
