package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockTestList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("locked")
    @Expose
    private Boolean locked;
    @SerializedName("number_of_tests")
    @Expose
    private String number_of_tests;

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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getNumber_of_tests() {
        return number_of_tests;
    }

    public void setNumber_of_tests(String number_of_tests) {
        this.number_of_tests = number_of_tests;
    }
}
