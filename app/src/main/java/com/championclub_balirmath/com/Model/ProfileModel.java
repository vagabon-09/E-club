package com.championclub_balirmath.com.Model;

public class ProfileModel {
    private String userName, userEmail, position, userPassword;

    public ProfileModel() {
    }

    public ProfileModel(String userName, String userEmail, String position, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.position = position;
        this.userPassword = userPassword;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


}
