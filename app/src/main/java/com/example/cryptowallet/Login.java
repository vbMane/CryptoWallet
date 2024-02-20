package com.example.cryptowallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptowallet.Admin.AdminLogin;
import com.example.cryptowallet.Model.Users;
import com.example.cryptowallet.User.UserDashboard;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextInputEditText mobileEditText, passEditText;
    private Button loginButton;
    private TextView registerTextView, adminLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileEditText = findViewById(R.id.MobileET);
        passEditText = findViewById(R.id.PassET);
        loginButton = findViewById(R.id.LoginBtn);
        registerTextView = findViewById(R.id.textView3);
        adminLoginTextView = findViewById(R.id.AdminLoginBtn);

        String check = getSharedPreferences("LoginData",0).getString("mob","e");
        if (!check.equals("e")){
            startActivity(new Intent(Login.this,UserDashboard.class));
        }

        adminLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, AdminLogin.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = mobileEditText.getText().toString().trim();
                String password = passEditText.getText().toString().trim();

                if (mobile.length() != 10) {
                    Toast.makeText(Login.this, "Please enter valid Mobile", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(Login.this, "All field Required.", Toast.LENGTH_SHORT).show();
                } else {

                    LoginUser(mobile, password);

                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }

    private void LoginUser(String mobile, String password) {
        Loading.startLoad(Login.this);

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(mobile).exists()) {

                    Users usersData = dataSnapshot.child("Users").child(mobile).child("Profile").getValue(Users.class);

                    if (usersData.getPhone().equals(mobile)) {
                        if (usersData.getPassword().equals(password)) {
                            if (usersData.getStatus().equals("V")) {

                                getSharedPreferences("LoginData", 0).edit().putString("mob", mobile).apply();

                                Toast.makeText(Login.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                Loading.startLoad(Login.this);

                                Intent intent = new Intent(Login.this, UserDashboard.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Loading.stopLoad();
                                Toast.makeText(Login.this, "Your Account Activated Soon...", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Loading.stopLoad();
                            Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "Account Rejected Or number do not exists.", Toast.LENGTH_SHORT).show();
                    Loading.stopLoad();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("error",databaseError.toString());
                Loading.stopLoad();
            }
        });





    }

}