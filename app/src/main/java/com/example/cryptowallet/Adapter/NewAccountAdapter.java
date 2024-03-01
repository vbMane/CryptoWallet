package com.example.cryptowallet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptowallet.Admin.CheckAccountRequest;
import com.example.cryptowallet.Model.Users;

import java.util.ArrayList;
import com.example.cryptowallet.R;

public class NewAccountAdapter extends RecyclerView.Adapter<NewAccountAdapter.Viewholder> {

    Context context;
    ArrayList<Users> arrayList;

    public NewAccountAdapter(Context context, ArrayList<Users> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.newaccountcard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Users po = arrayList.get(position);
        holder.textViewName.setText("Name: "+po.getFull_Name());
        holder.textViewMobile.setText("Mobile: "+po.getPhone());
        holder.textViewAddress.setText("Address: "+po.getAddress());
        holder.textViewDOB.setText("DOB: "+po.getDOB());
        Log.e("error",po.getUpiID());

        holder.itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CheckAccountRequest.class);

                intent.putExtra("name", po.getFull_Name());
                intent.putExtra("mobile", po.getPhone());
                intent.putExtra("address", po.getAddress());
                intent.putExtra("dob", po.getDOB());
                intent.putExtra("imgUrl", po.getPanImg());
                intent.putExtra("ADimgUrl", po.getAadhaar());
                intent.putExtra("UpiID", po.getUpiID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewMobile;
        private TextView textViewAddress;
        private TextView textViewDOB;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewMobile = itemView.findViewById(R.id.textViewMobile);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewDOB = itemView.findViewById(R.id.textViewDOB);
        }
    }
}
