package com.sm_academy.ModelClass.OngoingLiveSeesion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OngoingLiveSession {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("batch_id")
    @Expose
    private Integer batchId;
    @SerializedName("phase_id")
    @Expose
    private Integer phaseId;
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
    @SerializedName("phase_name")
    @Expose
    private String phaseName;
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
    @SerializedName("start_date_for_calendar")
    @Expose
    private String startDateForCalendar;
    @SerializedName("end_date_for_calendar")
    @Expose
    private String endDateForCalendar;
    @SerializedName("title_for_calendar")
    @Expose
    private String titleForCalendar;
    @SerializedName("title_for_admin_calendar")
    @Expose
    private String titleForAdminCalendar;
    @SerializedName("tooltip_for_calendar")
    @Expose
    private String tooltipForCalendar;
    @SerializedName("tooltip_for_trainer_calendar")
    @Expose
    private String tooltipForTrainerCalendar;
    @SerializedName("timings")
    @Expose
    private String timings;
    @SerializedName("trainer_name")
    @Expose
    private String trainerName;
    @SerializedName("attachment_id")
    @Expose
    private String attachmentId;
    @SerializedName("attachment_url")
    @Expose
    private String attachmentUrl;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("description")
    @Expose
    private String description;

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

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
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

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
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

    public String getStartDateForCalendar() {
        return startDateForCalendar;
    }

    public void setStartDateForCalendar(String startDateForCalendar) {
        this.startDateForCalendar = startDateForCalendar;
    }

    public String getEndDateForCalendar() {
        return endDateForCalendar;
    }

    public void setEndDateForCalendar(String endDateForCalendar) {
        this.endDateForCalendar = endDateForCalendar;
    }

    public String getTitleForCalendar() {
        return titleForCalendar;
    }

    public void setTitleForCalendar(String titleForCalendar) {
        this.titleForCalendar = titleForCalendar;
    }

    public String getTitleForAdminCalendar() {
        return titleForAdminCalendar;
    }

    public void setTitleForAdminCalendar(String titleForAdminCalendar) {
        this.titleForAdminCalendar = titleForAdminCalendar;
    }

    public String getTooltipForCalendar() {
        return tooltipForCalendar;
    }

    public void setTooltipForCalendar(String tooltipForCalendar) {
        this.tooltipForCalendar = tooltipForCalendar;
    }

    public String getTooltipForTrainerCalendar() {
        return tooltipForTrainerCalendar;
    }

    public void setTooltipForTrainerCalendar(String tooltipForTrainerCalendar) {
        this.tooltipForTrainerCalendar = tooltipForTrainerCalendar;
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

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
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
}
