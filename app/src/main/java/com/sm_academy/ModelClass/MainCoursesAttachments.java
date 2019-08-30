package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainCoursesAttachments {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("extension")
    @Expose
    private String extension;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
