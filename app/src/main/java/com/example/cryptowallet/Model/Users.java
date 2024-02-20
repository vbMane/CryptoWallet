package com.example.cryptowallet.Model;

public class Users {
    private String Full_Name, phone, DOB, address, PanImg,Status,password;

    public Users() {
    }

    public Users(String full_Name, String phone, String DOB, String address, String panImg, String status, String password) {
        Full_Name = full_Name;
        this.phone = phone;
        this.DOB = DOB;
        this.address = address;
        PanImg = panImg;
        Status = status;
        this.password = password;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPanImg() {
        return PanImg;
    }

    public void setPanImg(String panImg) {
        PanImg = panImg;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}