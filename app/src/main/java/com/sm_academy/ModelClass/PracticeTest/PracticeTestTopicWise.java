package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PracticeTestTopicWise {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number_of_questions")
    @Expose
    private Integer numberOfQuestios;
    @SerializedName("difficulty_level")
    @Expose
    private String difficultyLevel;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("number_of_attempts")
    @Expose
    private Integer numberOfAttempts;
    @SerializedName("already_taken")
    @Expose
    private String already_taken;

    public String getAlready_taken() {
        return already_taken;
    }

    public void setAlready_taken(String already_taken) {
        this.already_taken = already_taken;
    }

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

    public Integer getNumberOfQuestios() {
        return numberOfQuestios;
    }

    public void setNumberOfQuestios(Integer numberOfQuestios) {
        this.numberOfQuestios = numberOfQuestios;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(Integer numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

}
