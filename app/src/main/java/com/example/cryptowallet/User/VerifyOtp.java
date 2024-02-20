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
import com.example.cryptowallet.R;
import com.example.cryptowallet.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VerifyOtp extends AppCompatActivity {

    EditText editTextOTP;
    private FirebaseAuth auth;


    String phoneNumber;
    String Name;
    String DateOfBirth;
    String address;
    String pass;
    
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        auth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        String verificationId = intent.getStringExtra("verificationId");
        tag = intent.getStringExtra("tag");

        Name = intent.getStringExtra("Name");
        address = intent.getStringExtra("address");
        phoneNumber = intent.getStringExtra("phone");
        DateOfBirth = intent.getStringExtra("DOB");
        pass = intent.getStringExtra("pass");



        editTextOTP = findViewById(R.id.editTextOTP);
        findViewById(R.id.verifyOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Otp  = editTextOTP.getText().toString().trim();
                
                if (Otp.isEmpty()){
                    Toast.makeText(VerifyOtp.this, "Please enter otp ", Toast.LENGTH_SHORT).show();
                }else {
                    /*startActivity(new Intent(VerifyOtp.this, Kyc.class)
                            .putExtra("Name",name)
                            .putExtra("address",address)
                            .putExtra("phone",phoneNumber)
                            .putExtra("DOB",dateOfBirth)
                            .putExtra("pass",password));*/

                    Loading.startLoad(VerifyOtp.this);
                    verifyOTP(verificationId,Otp);
                }
            }
        });


    }

    private void verifyOTP(String verificationId, String otp) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        RegisterUser(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if (tag.equals("R")){

                            String uid = auth.getCurrentUser().getUid();
                            SaveData(uid);

                        }else if (tag.equals("L")){
                            startActivity(new Intent(VerifyOtp.this, UserDashboard.class));
                            finish();
                        }else {
                            Toast.makeText(this, "something went wrong try later", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                        Loading.stopLoad();
                    }
                });
    }

    private void SaveData(String uid) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("Full_Name", Name);
                        userdataMap.put("phone", phoneNumber);
                        userdataMap.put("DOB", DateOfBirth);
                        userdataMap.put("address", address );
                        userdataMap.put("PanImg", "NA" );
                        userdataMap.put("Status", "N" );
                        userdataMap.put("password", pass);

                        RootRef.child("Users").child(phoneNumber).child("Profile").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Loading.stopLoad();
                                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                                    Toast.makeText(VerifyOtp.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                } else {
                                    Loading.stopLoad();
                                    Toast.makeText(VerifyOtp.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(VerifyOtp.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error",databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });
    }

    private void RegisterUser(PhoneAuthCredential credential) {




    }
}