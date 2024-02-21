package com.example.cryptowallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptowallet.User.BUYCoin;
import com.example.cryptowallet.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SendCoin extends AppCompatActivity {

    Button sendBtn,showBtn;

    TextView UsdtAd;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_coin);

        showBtn = findViewById(R.id.showBtn);
        sendBtn = findViewById(R.id.sendBtn);
        UsdtAd = findViewById(R.id.UsdtAd);

        img = findViewById(R.id.img);

        String id = getIntent().getStringExtra("id");
        String Pname = getIntent().getStringExtra("Pname");
        String apy = getIntent().getStringExtra("apy");
        String value = getIntent().getStringExtra("value");
        String Lp = getIntent().getStringExtra("Lp");
        String trxid = getIntent().getStringExtra("trxid");

        String phoneNumber = getSharedPreferences("LoginData",0).getString("mob","");


        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsdtAd.setText(trxid);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumber).child("Deposit").child(id);
                Ref.removeValue();

                AddTOwithraw(id,Pname,apy,value,Lp,trxid);
            }
        });




    }

    private void AddTOwithraw(String id, String pname, String apy, String value, String lp, String trxid) {
        String phoneNumber = getSharedPreferences("LoginData",0).getString("mob","");

        Loading.startLoad(SendCoin.this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("Pname", pname);
                        userdataMap.put("apy", apy);
                        userdataMap.put("value", value);
                        userdataMap.put("lp", lp);
                        userdataMap.put("trxid", trxid);

                        RootRef.child("Users").child(phoneNumber).child("Withdraw").push().updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Loading.stopLoad();
                                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                                    Toast.makeText(SendCoin.this, "Successful!!", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                } else {
                                    Loading.stopLoad();
                                    Toast.makeText(SendCoin.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SendCoin.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error",databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });
    }
}