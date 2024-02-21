package com.example.cryptowallet.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.cryptowallet.Adapter.DepoCoinAdapter;
import com.example.cryptowallet.Adapter.WithdrawCoinAdapter;
import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Model.DpModel;
import com.example.cryptowallet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WithDrawCoins extends AppCompatActivity {

    RecyclerView recyclerView;

    WithdrawCoinAdapter adapter;

    ArrayList<DpModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw_coins);

        recyclerView = findViewById(R.id.WithdrawRVL);

        arrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(WithDrawCoins.this));
        recyclerView.setHasFixedSize(false);
        adapter = new WithdrawCoinAdapter(WithDrawCoins.this,arrayList);
        recyclerView.setAdapter(adapter);

        getData();
    }
    private void getData() {

        Loading.startLoad(WithDrawCoins.this);
        String phoneNumber = getSharedPreferences("LoginData",0).getString("mob","");

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference doctorEContactRef = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumber).child("Withdraw");

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
                            }
                            adapter.notifyDataSetChanged();
                        }
                        Loading.stopLoad();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(WithDrawCoins.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error", databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });


    }
}