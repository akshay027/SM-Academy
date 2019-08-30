package com.sm_academy.ModelClass.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sm_academy.ModelClass.Photo;

public class Learner {
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
    @SerializedName("pte_taken")
    @Expose
    private Boolean pteTaken;
    @SerializedName("photo")
    @Expose
    private Photo photo;

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

    public Boolean getPteTaken() {
        return pteTaken;
    }

    public void setPteTaken(Boolean pteTaken) {
        this.pteTaken = pteTaken;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}
