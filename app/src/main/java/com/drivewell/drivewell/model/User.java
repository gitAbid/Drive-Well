package com.drivewell.drivewell.model;

/**
 * Created by abid on 3/4/18.
 */

public class User {
    private String userId;
    private String name;
    private String age;
    private String email;
    private String password;
    private String confirmPassword;
    private String homeAddress;
    private String contactNo;
    private String userType;
    private String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public User(String userId, String name, String age, String email, String password, String confirmPassword, String homeAddress, String contactNo, String userType, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.homeAddress = homeAddress;
        this.contactNo = contactNo;
        this.userType = userType;
        this.profileImageUrl = profileImageUrl;
    }
}
