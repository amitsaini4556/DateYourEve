package com.example.dateyoureve;

public class User {
    String userName;
    String password;
    String email;
    String phone;
    String userId;

    public User()
    {

    }
    public User(String userId)
    {
        this.userId = userId;
    }
    public User(String userName,String email, String phone)
    {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }
    public  User(String email , String password){
        this.password = password;
        this.email = email;
    }

    public User(String userName, String password, String email, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
