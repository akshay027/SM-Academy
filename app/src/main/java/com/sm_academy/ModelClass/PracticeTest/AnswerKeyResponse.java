package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswerKeyResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("answers_key")
    @Expose
    private String answers_key;

    public String getAnswers_key() {
        return answers_key;
    }

    public void setAnswers_key(String answers_key) {
        this.answers_key = answers_key;
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

    @Override
    public String toString() {
        return "AnswerKeyResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", answers_key='" + answers_key + '\'' +
                '}';
    }
}
