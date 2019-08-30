package com.sm_academy.ModelClass.PracticeTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PracticeTest {
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("qp_attachment_type")
    @Expose
    private String qp_attachment_type;

    @SerializedName("options")
    @Expose
    private List<PracticeTestStart> options = null;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("passage")
    @Expose
    private String passage;

    @SerializedName("section_description")
    @Expose
    private String sectionDescription;
    @SerializedName("section_question")
    @Expose
    private String sectionQuestion;

    public String getSectionDescription() {
        return sectionDescription;
    }

    public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public String getSectionQuestion() {
        return sectionQuestion;
    }

    public void setSectionQuestion(String sectionQuestion) {
        this.sectionQuestion = sectionQuestion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getQp_attachment_type() {
        return qp_attachment_type;
    }

    public void setQp_attachment_type(String qp_attachment_type) {
        this.qp_attachment_type = qp_attachment_type;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PracticeTestStart> getOptions() {
        return options;
    }

    public void setOptions(List<PracticeTestStart> options) {
        this.options = options;

    }

    public PracticeTest(String sectionName, Integer id, String questionType, String question, List<PracticeTestStart> options, String description, String passage) {
        this.sectionName = sectionName;
        this.id = id;
        this.questionType = questionType;
        this.question = question;
        this.options = options;
        this.description = description;
        this.passage = passage;
    }

    @Override
    public String toString() {
        return "PracticeTest{" +
                "sectionName='" + sectionName + '\'' +
                ", id=" + id +
                ", questionType='" + questionType + '\'' +
                ", question='" + question + '\'' +
                ", attachment='" + attachment + '\'' +
                ", qp_attachment_type='" + qp_attachment_type + '\'' +
                ", options=" + options +
                ", description='" + description + '\'' +
                ", passage='" + passage + '\'' +
                ", sectionDescription='" + sectionDescription + '\'' +
                ", sectionQuestion='" + sectionQuestion + '\'' +
                '}';
    }
}
