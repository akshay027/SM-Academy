package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockTestFullScore {
    @SerializedName("question_number")
    @Expose
    private Integer questionNumber;
    @SerializedName("correct_answer")
    @Expose
    private String correctAnswer;
    @SerializedName("correct_answer_color")
    @Expose
    private String correctAnswerColor;
    @SerializedName("answered")
    @Expose
    private String answered;
    @SerializedName("answered_url")
    @Expose
    private String answeredUrl;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("feedback")
    @Expose
    private Object feedback;

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswerColor() {
        return correctAnswerColor;
    }

    public void setCorrectAnswerColor(String correctAnswerColor) {
        this.correctAnswerColor = correctAnswerColor;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public String getAnsweredUrl() {
        return answeredUrl;
    }

    public void setAnsweredUrl(String answeredUrl) {
        this.answeredUrl = answeredUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Object getFeedback() {
        return feedback;
    }

    public void setFeedback(Object feedback) {
        this.feedback = feedback;
    }


}
