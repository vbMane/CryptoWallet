package com.example.cryptowallet.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cryptowallet.Login;
import com.example.cryptowallet.R;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        setSupportActionBar(findViewById(R.id.toolbar));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.umenu_logout) {

            getSharedPreferences("LoginData", 0).edit().remove("mob").apply();

            startActivity(new Intent(UserDashboard.this, Login.class));
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}