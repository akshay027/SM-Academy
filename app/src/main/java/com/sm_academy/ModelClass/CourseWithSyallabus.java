package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseWithSyallabus {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("attachments")
    @Expose
    private List<CourseWithSyllabusDetails> attachments = null;
    @SerializedName("upcased_name")
    @Expose
    private String upcasedName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseWithSyllabusDetails> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CourseWithSyllabusDetails> attachments) {
        this.attachments = attachments;
    }

    public String getUpcasedName() {
        return upcasedName;
    }

    public void setUpcasedName(String upcasedName) {
        this.upcasedName = upcasedName;
    }

}
