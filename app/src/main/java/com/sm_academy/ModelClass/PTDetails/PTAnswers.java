package com.sm_academy.ModelClass.PTDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PTAnswers {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option_id_0")
    @Expose
    private Integer optionId0;
    @SerializedName("option_0")
    @Expose
    private String option0;
    @SerializedName("option_id_1")
    @Expose
    private Integer optionId1;
    @SerializedName("option_1")
    @Expose
    private String option1;
    @SerializedName("option_id_2")
    @Expose
    private Integer optionId2;
    @SerializedName("option_2")
    @Expose
    private String option2;
    @SerializedName("option_id_3")
    @Expose
    private Integer optionId3;
    @SerializedName("option_3")
    @Expose
    private String option3;
    private int checkedId = -1;

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getOptionId0() {
        return optionId0;
    }

    public void setOptionId0(Integer optionId0) {
        this.optionId0 = optionId0;
    }

    public String getOption0() {
        return option0;
    }

    public void setOption0(String option0) {
        this.option0 = option0;
    }

    public Integer getOptionId1() {
        return optionId1;
    }

    public void setOptionId1(Integer optionId1) {
        this.optionId1 = optionId1;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public Integer getOptionId2() {
        return optionId2;
    }

    public void setOptionId2(Integer optionId2) {
        this.optionId2 = optionId2;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public Integer getOptionId3() {
        return optionId3;
    }

    public void setOptionId3(Integer optionId3) {
        this.optionId3 = optionId3;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

}