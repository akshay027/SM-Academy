package com.sm_academy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SigninUser {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("highest_qualification")
    @Expose
    private String highestQualification;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("pt_taken")
    @Expose
    private String pteTaken;
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("active_course")
    @Expose
    private Boolean activeCourse;
    @SerializedName("login_condition_message")
    @Expose
    private String loginConditionMessage;

    public String getLoginConditionMessage() {
        return loginConditionMessage;
    }

    public void setLoginConditionMessage(String loginConditionMessage) {
        this.loginConditionMessage = loginConditionMessage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPteTaken() {
        return pteTaken;
    }

    public void setPteTaken(String pteTaken) {
        this.pteTaken = pteTaken;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Boolean getActiveCourse() {
        return activeCourse;
    }

    public void setActiveCourse(Boolean activeCourse) {
        this.activeCourse = activeCourse;
    }

    @Override
    public String toString() {
        return "SigninUser{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", highestQualification='" + highestQualification + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", username='" + username + '\'' +
                ", pteTaken='" + pteTaken + '\'' +
                ", gender='" + gender + '\'' +
                ", photo=" + photo +
                ", activeCourse=" + activeCourse +
                '}';
    }
}
