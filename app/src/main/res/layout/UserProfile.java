package com.example.student.arielexpress;

/**
 * Created by Dheeraj_Kamath on 2/10/2018.
 */

public class UserProfile {
    public String userAdress;
    public String userEmail;
    public String userName;
    public String userCredit;
    public String userPhone;

    public UserProfile(String userAdress, String userEmail, String userName, String userCredit, String userPhone) {
        this.userAdress = userAdress;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCredit = userCredit;
        this.userPhone = userPhone;
    }

    public UserProfile(){

    }


    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(String userCredit) {
        this.userCredit = userCredit;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}



