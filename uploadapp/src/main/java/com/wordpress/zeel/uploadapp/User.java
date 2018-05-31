package com.wordpress.zeel.uploadapp;

import com.google.firebase.database.Exclude;

import java.util.Comparator;

public class User {

    private String name,email,phone,password;
    private String key;
    private boolean isDisabled;

    public User(){
        // empty default constructor
    }

    public User(String name, String email, String phone, String password){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
