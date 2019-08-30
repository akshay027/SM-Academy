package com.sm_academy.ModelClass.LiveSession;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingSession {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("batch_id")
    @Expose
    private Integer batchId;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("section_topic_id")
    @Expose
    private Integer sectionTopicId;
    @SerializedName("session_date")
    @Expose
    private String sessionDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("section_topic_topic")
    @Expose
    private String sectionTopicTopic;
    @SerializedName("session_date_slash_format")
    @Expose
    private String sessionDateSlashFormat;
    @SerializedName("session_date_readable_format")
    @Expose
    private String sessionDateReadableFormat;
    @SerializedName("color_for_calendar")
    @Expose
    private String colorForCalendar;
    @SerializedName("date_for_calendar")
    @Expose
    private String dateForCalendar;
    @SerializedName("title_for_admin_calendar")
    @Expose
    private String titleForAdminCalendar;
    @SerializedName("title_for_learner_calendar")
    @Expose
    private String titleForLearnerCalendar;
    @SerializedName("title_for_trainer_calendar")
    @Expose
    private String titleForTrainerCalendar;
    @SerializedName("timings")
    @Expose
    private String timings;
    @SerializedName("trainer_name")
    @Expose
    private String trainerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getSectionTopicId() {
        return sectionTopicId;
    }

    public void setSectionTopicId(Integer sectionTopicId) {
        this.sectionTopicId = sectionTopicId;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionTopicTopic() {
        return sectionTopicTopic;
    }

    public void setSectionTopicTopic(String sectionTopicTopic) {
        this.sectionTopicTopic = sectionTopicTopic;
    }

    public String getSessionDateSlashFormat() {
        return sessionDateSlashFormat;
    }

    public void setSessionDateSlashFormat(String sessionDateSlashFormat) {
        this.sessionDateSlashFormat = sessionDateSlashFormat;
    }

    public String getSessionDateReadableFormat() {
        return sessionDateReadableFormat;
    }

    public void setSessionDateReadableFormat(String sessionDateReadableFormat) {
        this.sessionDateReadableFormat = sessionDateReadableFormat;
    }

    public String getColorForCalendar() {
        return colorForCalendar;
    }

    public void setColorForCalendar(String colorForCalendar) {
        this.colorForCalendar = colorForCalendar;
    }

    public String getDateForCalendar() {
        return dateForCalendar;
    }

    public void setDateForCalendar(String dateForCalendar) {
        this.dateForCalendar = dateForCalendar;
    }

    public String getTitleForAdminCalendar() {
        return titleForAdminCalendar;
    }

    public void setTitleForAdminCalendar(String titleForAdminCalendar) {
        this.titleForAdminCalendar = titleForAdminCalendar;
    }

    public String getTitleForLearnerCalendar() {
        return titleForLearnerCalendar;
    }

    public void setTitleForLearnerCalendar(String titleForLearnerCalendar) {
        this.titleForLearnerCalendar = titleForLearnerCalendar;
    }

    public String getTitleForTrainerCalendar() {
        return titleForTrainerCalendar;
    }

    public void setTitleForTrainerCalendar(String titleForTrainerCalendar) {
        this.titleForTrainerCalendar = titleForTrainerCalendar;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @Override
    public String toString() {
        return "UpcomingSession{" +
                "id=" + id +
                ", batchId=" + batchId +
                ", sectionId=" + sectionId +
                ", sectionTopicId=" + sectionTopicId +
                ", sessionDate='" + sessionDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", trainerId=" + trainerId +
                ", batchName='" + batchName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", sectionTopicTopic='" + sectionTopicTopic + '\'' +
                ", sessionDateSlashFormat='" + sessionDateSlashFormat + '\'' +
                ", sessionDateReadableFormat='" + sessionDateReadableFormat + '\'' +
                ", colorForCalendar='" + colorForCalendar + '\'' +
                ", dateForCalendar='" + dateForCalendar + '\'' +
                ", titleForAdminCalendar='" + titleForAdminCalendar + '\'' +
                ", titleForLearnerCalendar='" + titleForLearnerCalendar + '\'' +
                ", titleForTrainerCalendar='" + titleForTrainerCalendar + '\'' +
                ", timings='" + timings + '\'' +
                ", trainerName='" + trainerName + '\'' +
                '}';
    }
}
