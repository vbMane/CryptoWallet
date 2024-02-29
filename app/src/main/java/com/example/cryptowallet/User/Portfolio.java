package com.example.cryptowallet.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptowallet.Adapter.DepoCoinAdapter;
import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Model.DpModel;
import com.example.cryptowallet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Portfolio extends AppCompatActivity {

    TextView TPvalueTV;

    RecyclerView recyclerView;

    int TValue = 0;

    DepoCoinAdapter adapter;

    ArrayList<DpModel> arrayList;
    Button button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);


        recyclerView= findViewById(R.id.MyPlanRVL);
        TPvalueTV = findViewById(R.id.TPvalueTV);

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);


        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(Portfolio.this));
        recyclerView.setHasFixedSize(false);
        adapter = new DepoCoinAdapter(Portfolio.this,arrayList);
        recyclerView.setAdapter(adapter);

        getData();


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Portfolio.this,WithDrawCoins.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Portfolio.this,DepositeCoins.class));
            }
        });





    }

    private void getData() {

        Loading.startLoad(Portfolio.this);
        String phoneNumber = getSharedPreferences("LoginData",0).getString("mob","");

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference doctorEContactRef = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumber).child("Deposit");

                doctorEContactRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            for (DataSnapshot contactSnapshot : dataSnapshot.getChildren()) {
                                Log.e("data", contactSnapshot.toString());

                                String cid = contactSnapshot.getKey();
                                String Pname = contactSnapshot.child("Pname").getValue(String.class);
                                String apy = contactSnapshot.child("apy").getValue(String.class);
                                String value = contactSnapshot.child("value").getValue(String.class);
                                String lp = contactSnapshot.child("lp").getValue(String.class);
                                String trxid = contactSnapshot.child("trxid").getValue(String.class);

                                arrayList.add(new DpModel(cid,Pname,apy,value,lp,trxid));

                                int pv = Integer.parseInt(value);
                                TValue = TValue + pv;
                            }
                            TPvalueTV.setText(String.valueOf(TValue));
                            adapter.notifyDataSetChanged();
                        }
                        Loading.stopLoad();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Portfolio.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error", databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });


    }
}