package com.example.cryptowallet.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Login;
import com.example.cryptowallet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BUYCoin extends AppCompatActivity {

    EditText trxIDET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buycoin);


        trxIDET = findViewById(R.id.trxIDET);
        String Pname = getIntent().getStringExtra("Pname");
        String apy = getIntent().getStringExtra("apy");
        String value = getIntent().getStringExtra("value");
        String Lp = getIntent().getStringExtra("Lp");

        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String trxid = trxIDET.getText().toString().trim();

                if (trxid.isEmpty()){
                    Toast.makeText(BUYCoin.this, "Please enter Transaction ID", Toast.LENGTH_SHORT).show();
                }else {
                    AddToDp(Pname,apy,value,Lp,trxid);
                }

            }
        });
    }

    private void AddToDp(String pname, String apy, String value, String lp, String trxid) {

        String phoneNumber = getSharedPreferences("LoginData",0).getString("mob","");

        Loading.startLoad(BUYCoin.this);
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

                        RootRef.child("Users").child(phoneNumber).child("Deposit").push().updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Loading.stopLoad();
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    Toast.makeText(BUYCoin.this, "Successful!!", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                } else {
                                    Loading.stopLoad();
                                    Toast.makeText(BUYCoin.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(BUYCoin.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error",databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });
    }
}