package com.sm_academy.ModelClass.StudyMaterialView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyMaterialsDetails {
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("filename")
    @Expose
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

}
