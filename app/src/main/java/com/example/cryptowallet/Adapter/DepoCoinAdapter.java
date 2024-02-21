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

import com.example.cryptowallet.Model.AllPlansModel;
import com.example.cryptowallet.Model.DpModel;
import com.example.cryptowallet.R;
import com.example.cryptowallet.SendCoin;
import com.example.cryptowallet.User.BUYCoin;
import com.example.cryptowallet.User.DepositeCoins;

import java.util.ArrayList;

public class DepoCoinAdapter extends RecyclerView.Adapter<DepoCoinAdapter.Viewholder> {

    Context context;
    ArrayList<DpModel> arrayList;

    public DepoCoinAdapter(Context context, ArrayList<DpModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.planscard_fordp,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        DpModel po = arrayList.get(position);
        holder.PlanTitle.setText(po.getPname());
        holder.apyTV.setText(po.getApy()+" % APY");
        holder.valueTV.setText(po.getValue());
        holder.LpTV.setText("Locking Period : "+po.getLp()+" yr");

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SendCoin.class)
                        .putExtra("id",po.getId())
                        .putExtra("Pname",po.getPname())
                        .putExtra("apy",po.getApy())
                        .putExtra("value",po.getValue())
                        .putExtra("Lp",po.getLp())
                        .putExtra("trxid",po.getTrxid()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView PlanTitle,apyTV,valueTV,LpTV;

        Button send;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            send = itemView.findViewById(R.id.SendBtn);
            PlanTitle = itemView.findViewById(R.id.PlanTitle);
            apyTV = itemView.findViewById(R.id.apyTV);
            valueTV = itemView.findViewById(R.id.valueTV);
            LpTV = itemView.findViewById(R.id.LpTV);

        }
    }
}
