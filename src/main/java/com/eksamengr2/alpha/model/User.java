package com.eksamengr2.alpha.model;

public class User {
    private String password;
    private String userName;
    private String userType;

    public User(String password, String userName) {
        this.password = password;
        this.userName = userName;
    }

    public User() {//(TL, EB, MS)
    }

    public User(String password, String userName, String userType) {
        this.password = password;
        this.userName = userName;
        this.userType = userType;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}

