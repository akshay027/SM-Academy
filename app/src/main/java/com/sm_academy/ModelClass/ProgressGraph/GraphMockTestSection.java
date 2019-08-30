package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphMockTestSection {
    @SerializedName("test_names")
    @Expose
    private List<String> testNames = null;
    @SerializedName("scores")
    @Expose
    private List<Integer> scores = null;
    @SerializedName("is_tests_present")
    @Expose
    private Boolean isTestsPresent;

    public List<String> getTestNames() {
        return testNames;
    }

    public void setTestNames(List<String> testNames) {
        this.testNames = testNames;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public Boolean getIsTestsPresent() {
        return isTestsPresent;
    }

    public void setIsTestsPresent(Boolean isTestsPresent) {
        this.isTestsPresent = isTestsPresent;
    }

}
