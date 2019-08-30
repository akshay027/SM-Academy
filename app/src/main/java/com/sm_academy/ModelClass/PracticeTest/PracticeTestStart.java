package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PracticeTestStart {
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("option")
    @Expose
    private String option;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


    private Integer check_box = 2;

    public Integer getCheck_box() {
        return check_box;
    }

    public void setCheck_box(Integer check_box) {
        this.check_box = check_box;
    }

    @Override
    public String toString() {
        return "PracticeTestStart{" +
                "index='" + index + '\'' +
                ", option='" + option + '\'' +
                ", check_box=" + check_box +
                '}';
    }
}
