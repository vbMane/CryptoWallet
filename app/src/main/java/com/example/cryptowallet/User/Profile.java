package com.example.cryptowallet.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cryptowallet.Login;
import com.example.cryptowallet.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.LogoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("LoginData", 0).edit().remove("mob").apply();
                startActivity(new Intent(Profile.this, Login.class));
                finishAffinity();
            }
        });
    }
}