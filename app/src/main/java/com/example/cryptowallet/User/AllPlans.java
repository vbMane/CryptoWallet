package com.example.cryptowallet.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.example.cryptowallet.Adapter.AllPlansAdapter;
import com.example.cryptowallet.Model.AllPlansModel;
import com.example.cryptowallet.R;

import java.util.ArrayList;
import java.util.Random;

public class AllPlans extends AppCompatActivity {

    RecyclerView recyclerView;

    AllPlansAdapter adapter;

    ArrayList<AllPlansModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_plans);

        recyclerView = findViewById(R.id.AllPlansRVL);

        arrayList = new ArrayList<>();



        recyclerView.setLayoutManager(new LinearLayoutManager(AllPlans.this));
        recyclerView.setHasFixedSize(false);
        adapter = new AllPlansAdapter(AllPlans.this,arrayList);
        recyclerView.setAdapter(adapter);




        updateData();
    }

    private void updateData() {

        arrayList.clear();
        arrayList.add(new AllPlansModel("Basic - TRX","11",getvalue(900,1200),"1"));
        arrayList.add(new AllPlansModel("Basic - USDT","10",getvalue(90,110),"1"));
        arrayList.add(new AllPlansModel("Sliver - TRX","14",getvalue(1900,2100),"1"));
        arrayList.add(new AllPlansModel("Sliver - USDT","12",getvalue(190,210),"1"));
        arrayList.add(new AllPlansModel("Gold - TRX","12",getvalue(4900,5100),"1"));
        arrayList.add(new AllPlansModel("Gold - USDT","12",getvalue(490,510),"1"));
        arrayList.add(new AllPlansModel("Platinum - TRX","18",getvalue(9900,10100),"1.5"));
        arrayList.add(new AllPlansModel("Platinum - USDT","18",getvalue(900,1100),"1.5"));
        adapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData();
            }
        },5000);


    }


    private String getvalue(int min, int max) {

        int randomValue = new Random().nextInt(max - min) + min;

        return String.valueOf(randomValue);
    }



}