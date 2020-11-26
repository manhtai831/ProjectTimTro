package com.example.myduan1.model;

public class User {
    private String userName, name, gender, phoneNumber, password;

    public User() {
    }

    public User(String userName, String name, String gender, String phoneNumber, String password) {
        this.userName = userName;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
