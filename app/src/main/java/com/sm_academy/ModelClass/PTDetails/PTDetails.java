package com.sm_academy.ModelClass.PTDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PTDetails {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pt_questions")
    @Expose
    private List<PTAnswers> ptQuestions = null;
    @SerializedName("about_the_test")
    @Expose
    private String aboutTheTest;

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

    public List<PTAnswers> getPtQuestions() {
        return ptQuestions;
    }

    public void setPtQuestions(List<PTAnswers> ptQuestions) {
        this.ptQuestions = ptQuestions;
    }

    public String getAboutTheTest() {
        return aboutTheTest;
    }

    public void setAboutTheTest(String aboutTheTest) {
        this.aboutTheTest = aboutTheTest;
    }

}