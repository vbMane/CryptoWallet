package com.example.cryptowallet.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Login;
import com.example.cryptowallet.Model.DpModel;
import com.example.cryptowallet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    TextView NameTV,DOBTV,addressTV,phoneTV,UpiIDTV;

    TextView withBtn,dpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        NameTV = findViewById(R.id.NameTV);
        DOBTV = findViewById(R.id.DOBTV);
        addressTV = findViewById(R.id.AddressTV);
        phoneTV = findViewById(R.id.phoneTV);
        UpiIDTV = findViewById(R.id.UpiIDTV);

        withBtn= findViewById(R.id.withBtn);
        dpBtn= findViewById(R.id.DpBtn);


        findViewById(R.id.potBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Profile.this,Portfolio.class));
            }
        });

        withBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,WithDrawCoins.class));
            }
        });

        dpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,DepositeCoins.class));
            }
        });


        findViewById(R.id.LogoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("LoginData", 0).edit().remove("mob").apply();
                startActivity(new Intent(Profile.this, Login.class));
                finishAffinity();
            }
        });

        getData();
    }

    private void getData() {

        Loading.startLoad(Profile.this);
        String phoneNumber = getSharedPreferences("LoginData",0).getString("mob","");

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference doctorEContactRef = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumber).child("Profile");

                doctorEContactRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        String cid = dataSnapshot.getKey();
                        String Full_Name = dataSnapshot.child("Full_Name").getValue(String.class);
                        String address = dataSnapshot.child("address").getValue(String.class);
                        String phone = dataSnapshot.child("phone").getValue(String.class);
                        String DOB = dataSnapshot.child("DOB").getValue(String.class);
                        String UpiID = dataSnapshot.child("UpiID").getValue(String.class);

                        NameTV.setText(Full_Name);
                        addressTV.setText(address);
                        phoneTV.setText(phone);
                        DOBTV.setText(DOB);
                        UpiIDTV.setText(UpiID);
                        Loading.stopLoad();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Profile.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error", databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });


    }

}