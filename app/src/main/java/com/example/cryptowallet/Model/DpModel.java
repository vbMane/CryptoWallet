package com.example.cryptowallet.Model;

public class DpModel {

    String id,pname,apy,value,lp,trxid;

    public DpModel(String id, String pname, String apy, String value, String lp, String trxid) {
        this.id = id;
        this.pname = pname;
        this.apy = apy;
        this.value = value;
        this.lp = lp;
        this.trxid = trxid;
    }

    public String getId() {
        return id;
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

    public String getTrxid() {
        return trxid;
    }
}
