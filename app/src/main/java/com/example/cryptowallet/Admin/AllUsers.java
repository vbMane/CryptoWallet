package com.example.cryptowallet.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cryptowallet.Adapter.AllUserAdapter;
import com.example.cryptowallet.Adapter.NewAccountAdapter;
import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Model.Users;
import com.example.cryptowallet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUsers extends AppCompatActivity {

    RecyclerView recyclerView;

    AllUserAdapter adapter;
    ArrayList<Users> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.AllUserRVL);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllUsers.this));
        recyclerView.setHasFixedSize(false);
        adapter = new AllUserAdapter(AllUsers.this,arrayList);
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {


        Loading.startLoad(AllUsers.this);

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

                                if (usersData.getStatus().equals("V")){
                                    arrayList.add(usersData);
                                }

                            }
                            adapter.notifyDataSetChanged();
                        }
                        Loading.stopLoad();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(AllUsers.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error", databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });
    }
}