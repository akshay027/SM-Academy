package com.sm_academy.ModelClass;

public class PushNotifications {
    private String section_id;
    private String section_name;
    private String section_topic_id;
    private String assignment_id;
    private String date;
    private String time;
    private String live_session_id;
    private String mock_test_id;

    private String mock_test_category_id;

    public String getLive_session_id() {
        return live_session_id;
    }

    public void setLive_session_id(String live_session_id) {
        this.live_session_id = live_session_id;
    }

    public String getMock_test_id() {
        return mock_test_id;
    }

    public void setMock_test_id(String mock_test_id) {
        this.mock_test_id = mock_test_id;
    }

    public String getMock_test_category_id() {
        return mock_test_category_id;
    }

    public void setMock_test_category_id(String mock_test_category_id) {
        this.mock_test_category_id = mock_test_category_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_topic_id() {
        return section_topic_id;
    }

    public void setSection_topic_id(String section_topic_id) {
        this.section_topic_id = section_topic_id;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(String question_paper_id) {
        this.assignment_id = question_paper_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
