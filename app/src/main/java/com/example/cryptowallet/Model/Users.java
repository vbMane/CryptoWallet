package com.example.cryptowallet.Model;

public class Users {
    private String Full_Name, phone, DOB, address,UpiID,Aadhaar, PanImg,Status,password;

    public Users() {
    }

    public Users(String full_Name, String phone, String DOB, String address, String aadhaar, String upiID, String panImg, String status, String password) {
        Full_Name = full_Name;
        this.phone = phone;
        this.DOB = DOB;
        this.address = address;
        Aadhaar = aadhaar;
        UpiID = upiID;
        PanImg = panImg;
        Status = status;
        this.password = password;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDOB() {
        return DOB;
    }

    public String getAddress() {
        return address;
    }

    public String getAadhaar() {
        return Aadhaar;
    }

    public String getUpiID() {
        return UpiID;
    }

    public String getPanImg() {
        return PanImg;
    }

    public String getStatus() {
        return Status;
    }

    public String getPassword() {
        return password;
    }
}