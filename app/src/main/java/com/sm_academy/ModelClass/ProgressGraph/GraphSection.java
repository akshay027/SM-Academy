package com.sm_academy.ModelClass.ProgressGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GraphSection {
    @SerializedName("section_name")
    @Expose
    private String section_name;
    @SerializedName("question_paper_names")
    @Expose
    private ArrayList<String> question_paper_names = null;
    @SerializedName("question_paper_scores")
    @Expose
    private ArrayList<Integer> question_paper_scores = null;
/*    @SerializedName("is_tests_present")
    @Expose
    private String is_tests_present;*/

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public ArrayList<String> getQuestion_paper_names() {
        return question_paper_names;
    }

    public void setQuestion_paper_names(ArrayList<String> question_paper_names) {
        this.question_paper_names = question_paper_names;
    }

    public List<Integer> getQuestion_paper_scores() {
        return question_paper_scores;
    }

    public void setQuestion_paper_scores(ArrayList<Integer> question_paper_scores) {
        this.question_paper_scores = question_paper_scores;
    }

   /* public String getIs_tests_present() {
        return is_tests_present;
    }

    public void setIs_tests_present(String is_tests_present) {
        this.is_tests_present = is_tests_present;
    }*/

    @Override
    public String toString() {
        return "GraphSection{" +
                "section_name='" + section_name + '\'' +
                ", question_paper_names=" + question_paper_names +
                ", question_paper_scores=" + question_paper_scores +
                '}';
    }
}
