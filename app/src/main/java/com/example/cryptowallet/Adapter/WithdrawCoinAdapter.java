package com.example.cryptowallet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptowallet.Model.DpModel;
import com.example.cryptowallet.R;
import com.example.cryptowallet.SendCoin;

import java.util.ArrayList;

public class WithdrawCoinAdapter extends RecyclerView.Adapter<WithdrawCoinAdapter.Viewholder> {

    Context context;
    ArrayList<DpModel> arrayList;

    public WithdrawCoinAdapter(Context context, ArrayList<DpModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.planscard_forwithdraw,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        DpModel po = arrayList.get(position);
        holder.PlanTitle.setText(po.getPname());
        holder.apyTV.setText(po.getApy()+" % APY");
        holder.valueTV.setText(po.getValue());
        holder.LpTV.setText("Locking Period : "+po.getLp()+" yr");



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView PlanTitle,apyTV,valueTV,LpTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            PlanTitle = itemView.findViewById(R.id.PlanTitle);
            apyTV = itemView.findViewById(R.id.apyTV);
            valueTV = itemView.findViewById(R.id.valueTV);
            LpTV = itemView.findViewById(R.id.LpTV);

        }
    }
}
