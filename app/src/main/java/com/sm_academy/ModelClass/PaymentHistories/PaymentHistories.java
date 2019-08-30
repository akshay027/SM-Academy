package com.sm_academy.ModelClass.PaymentHistories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistories {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("batch_user_id")
    @Expose
    private Integer batchUserId;
    @SerializedName("extended_days")
    @Expose
    private String extendedDays;
    @SerializedName("mode_of_payment")
    @Expose
    private String modeOfPayment;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("receipt_number")
    @Expose
    private String receiptNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("batch_name")
    @Expose
    private String batch_name;
    @SerializedName("course_name")
    @Expose
    private String course_name;
    @SerializedName("payment_for")
    @Expose
    private String payment_for;

    public String getPayment_for() {
        return payment_for;
    }

    public void setPayment_for(String payment_for) {
        this.payment_for = payment_for;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBatchUserId() {
        return batchUserId;
    }

    public void setBatchUserId(Integer batchUserId) {
        this.batchUserId = batchUserId;
    }

    public String getExtendedDays() {
        return extendedDays;
    }

    public void setExtendedDays(String extendedDays) {
        this.extendedDays = extendedDays;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
