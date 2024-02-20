package com.example.cryptowallet;

import android.app.ProgressDialog;
import android.content.Context;

public class Loading {

    static ProgressDialog progressDialog;

    public static void startLoad(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void stopLoad(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
