package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PracticeTestResult {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("time_taken")
    @Expose
    private String timeTaken;
    @SerializedName("total_questions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("correct_answers")
    @Expose
    private Integer correctAnswers;
    @SerializedName("wrong_answers")
    @Expose
    private Integer wrongAnswers;
    @SerializedName("percentage")
    @Expose
    private Double percentage;
    @SerializedName("to_be_evaluated")
    @Expose
    private Boolean to_be_evaluated;

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

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public Boolean getTo_be_evaluated() {
        return to_be_evaluated;
    }

    public void setTo_be_evaluated(Boolean to_be_evaluated) {
        this.to_be_evaluated = to_be_evaluated;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(Integer wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
