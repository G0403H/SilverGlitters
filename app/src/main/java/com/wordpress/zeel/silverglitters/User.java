package com.wordpress.zeel.silverglitters;

public class User {

    public String name,email,phone,password;
    public boolean disabled;
    public User(){

    }
    public User(String name,String email,String phone,String password){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
        this.disabled=false;
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

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }



}
