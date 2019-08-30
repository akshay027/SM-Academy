package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockTestStartResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tests")
    @Expose
    private List<PracticeTest> tests = null;
    @SerializedName("submission_id")
    @Expose
    private Integer submissionId;
    @SerializedName("question_number")
    @Expose
    private Integer questionNumber;
    @SerializedName("section_position")
    @Expose
    private Integer sectionPosition;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("question_paper_position")
    @Expose
    private Integer questionPaperPosition;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question_paper_id")
    @Expose
    private String question_paper_id;

    public String getQuestion_paper_id() {
        return question_paper_id;
    }

    public void setQuestion_paper_id(String question_paper_id) {
        this.question_paper_id = question_paper_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<PracticeTest> getTests() {
        return tests;
    }

    public void setTests(List<PracticeTest> tests) {
        this.tests = tests;
    }

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getSectionPosition() {
        return sectionPosition;
    }

    public void setSectionPosition(Integer sectionPosition) {
        this.sectionPosition = sectionPosition;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getQuestionPaperPosition() {
        return questionPaperPosition;
    }

    public void setQuestionPaperPosition(Integer questionPaperPosition) {
        this.questionPaperPosition = questionPaperPosition;
    }

}
