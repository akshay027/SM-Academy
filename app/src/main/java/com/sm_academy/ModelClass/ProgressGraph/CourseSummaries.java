package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseSummaries {
    @SerializedName("number_of_tests")
    @Expose
    private Integer numberOfTests;
    @SerializedName("attempted_tests_count")
    @Expose
    private Integer attemptedTestsCount;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("progress_percentage")
    @Expose
    private Integer progressPercentage;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public Integer getNumberOfTests() {
        return numberOfTests;
    }

    public void setNumberOfTests(Integer numberOfTests) {
        this.numberOfTests = numberOfTests;
    }

    public Integer getAttemptedTestsCount() {
        return attemptedTestsCount;
    }

    public void setAttemptedTestsCount(Integer attemptedTestsCount) {
        this.attemptedTestsCount = attemptedTestsCount;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
