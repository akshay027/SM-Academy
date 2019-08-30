package com.sm_academy.ModelClass.AboutCourse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseData {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;
    @SerializedName("buttons")
    @Expose
    private List<String> buttons = null;
    @SerializedName("button_message")
    @Expose
    private String buttonMessage;

    public String getButtonMessage() {
        return buttonMessage;
    }

    public void setButtonMessage(String buttonMessage) {
        this.buttonMessage = buttonMessage;
    }

    public List<String> getButtons() {
        return buttons;
    }

    public void setButtons(List<String> buttons) {
        this.buttons = buttons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
