package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("file_name")
    @Expose
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", attachment='" + attachment + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
