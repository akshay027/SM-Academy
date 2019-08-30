package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchDetailsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("batches")
    @Expose
    private List<BatchDetails> batches = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("assigned_batches_ids")
    @Expose
    private List<Integer> assignedBatchesIds = null;
    @SerializedName("can_enroll")
    @Expose
    private Boolean canEnroll;

    public Boolean getCanEnroll() {
        return canEnroll;
    }

    public void setCanEnroll(Boolean canEnroll) {
        this.canEnroll = canEnroll;
    }

    public List<Integer> getAssignedBatchesIds() {
        return assignedBatchesIds;
    }

    public void setAssignedBatchesIds(List<Integer> assignedBatchesIds) {
        this.assignedBatchesIds = assignedBatchesIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BatchDetails> getBatches() {
        return batches;
    }

    public void setBatches(List<BatchDetails> batches) {
        this.batches = batches;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
