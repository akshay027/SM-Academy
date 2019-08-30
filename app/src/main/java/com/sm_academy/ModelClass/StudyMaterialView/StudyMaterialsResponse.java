package com.sm_academy.ModelClass.StudyMaterialView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudyMaterialsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("study_materials")
    @Expose
    private List<StudyMaterialsDetails> studyMaterials = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StudyMaterialsDetails> getStudyMaterials() {
        return studyMaterials;
    }

    public void setStudyMaterials(List<StudyMaterialsDetails> studyMaterials) {
        this.studyMaterials = studyMaterials;
    }

}
