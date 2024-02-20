package com.example.cryptowallet.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cryptowallet.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLogin extends AppCompatActivity {

    private TextInputEditText usernameEditText, passEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        usernameEditText = findViewById(R.id.usernameET);
        passEditText = findViewById(R.id.PassET);
        loginButton = findViewById(R.id.LoginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uname = usernameEditText.getText().toString().trim();
                String pass = passEditText.getText().toString().trim();

                if (Uname.equals("a")&&pass.equals("a")){

                    startActivity(new Intent(AdminLogin.this,AdminDashboard.class));
                    finishAffinity();

                }else {
                    Toast.makeText(AdminLogin.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}