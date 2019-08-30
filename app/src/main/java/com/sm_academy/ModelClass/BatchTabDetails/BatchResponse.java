package com.sm_academy.ModelClass.BatchTabDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("upcoming_batches")
    @Expose
    private List<Upcomingbatches> upcomingBatches = null;
    @SerializedName("ongoing_batches")
    @Expose
    private List<Ongoingbatches> ongoingBatches = null;
    @SerializedName("learner_batch")
    @Expose
    private Batchlearnerbatch learnerBatch;
    @SerializedName("can_enroll")
    @Expose
    private Boolean canEnroll;

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

    public List<Upcomingbatches> getUpcomingBatches() {
        return upcomingBatches;
    }

    public void setUpcomingBatches(List<Upcomingbatches> upcomingBatches) {
        this.upcomingBatches = upcomingBatches;
    }

    public List<Ongoingbatches> getOngoingBatches() {
        return ongoingBatches;
    }

    public void setOngoingBatches(List<Ongoingbatches> ongoingBatches) {
        this.ongoingBatches = ongoingBatches;
    }

    public Batchlearnerbatch getLearnerBatch() {
        return learnerBatch;
    }

    public void setLearnerBatch(Batchlearnerbatch learnerBatch) {
        this.learnerBatch = learnerBatch;
    }

    public Boolean getCanEnroll() {
        return canEnroll;
    }

    public void setCanEnroll(Boolean canEnroll) {
        this.canEnroll = canEnroll;
    }

}
