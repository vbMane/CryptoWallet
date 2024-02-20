package com.example.cryptowallet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cryptowallet.Adapter.NewAccountAdapter;
import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Login;
import com.example.cryptowallet.Model.Users;
import com.example.cryptowallet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView recyclerView;

    NewAccountAdapter adapter;
    ArrayList<Users> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        setSupportActionBar(findViewById(R.id.toolbar));



        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.AccountListRVL);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminDashboard.this));
        recyclerView.setHasFixedSize(false);
        adapter = new NewAccountAdapter(AdminDashboard.this,arrayList);
        recyclerView.setAdapter(adapter);

        getData();

    }

    private void getData() {


        Loading.startLoad(AdminDashboard.this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Users");

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            for (DataSnapshot contactSnapshot : dataSnapshot.getChildren()) {
                                Log.e("data", contactSnapshot.toString());

                                Users usersData = contactSnapshot.child("Profile").getValue(Users.class);

                                if (usersData.getStatus().equals("NV")){
                                    arrayList.add(usersData);
                                }

                            }
                            adapter.notifyDataSetChanged();
                            findViewById(R.id.nodata).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.nodata).setVisibility(View.VISIBLE);
                            Toast.makeText(AdminDashboard.this, "No new Account found", Toast.LENGTH_SHORT).show();
                        }
                        Loading.stopLoad();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(AdminDashboard.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error", databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.amenu_logout){

            startActivity(new Intent(AdminDashboard.this, Login.class));
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}