package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockResult {
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("time_taken")
    @Expose
    private String timeTaken;
    @SerializedName("total_questions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("answered")
    @Expose
    private Integer answered;
    @SerializedName("correct_answers")
    @Expose
    private Integer correctAnswers;
    @SerializedName("wrong_answers")
    @Expose
    private Integer wrongAnswers;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getAnswered() {
        return answered;
    }

    public void setAnswered(Integer answered) {
        this.answered = answered;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
