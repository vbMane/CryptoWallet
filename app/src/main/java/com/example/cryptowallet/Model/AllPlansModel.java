package com.example.cryptowallet.Model;

public class AllPlansModel {

    String pname,apy,value,lp;

    public AllPlansModel(String pname, String apy, String value, String lp) {
        this.pname = pname;
        this.apy = apy;
        this.value = value;
        this.lp = lp;
    }

    public String getPname() {
        return pname;
    }

    public String getApy() {
        return apy;
    }

    public String getValue() {
        return value;
    }

    public String getLp() {
        return lp;
    }
}
