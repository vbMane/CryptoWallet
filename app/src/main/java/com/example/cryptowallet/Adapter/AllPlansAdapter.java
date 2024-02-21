package com.example.cryptowallet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptowallet.Model.AllPlansModel;
import com.example.cryptowallet.R;
import com.example.cryptowallet.User.BUYCoin;

import java.util.ArrayList;

public class AllPlansAdapter extends RecyclerView.Adapter<AllPlansAdapter.Viewholder> {

    Context context;
    ArrayList<AllPlansModel> arrayList;

    public AllPlansAdapter(Context context, ArrayList<AllPlansModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.planscard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        AllPlansModel po = arrayList.get(position);
        holder.PlanTitle.setText(po.getPname());
        holder.apyTV.setText(po.getApy()+" % APY");
        holder.valueTV.setText(po.getValue());
        holder.LpTV.setText("Locking Period : "+po.getLp()+" yr");

        holder.BuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, BUYCoin.class)
                        .putExtra("Pname",po.getPname())
                        .putExtra("apy",po.getApy())
                        .putExtra("value",po.getValue())
                        .putExtra("Lp",po.getLp()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView PlanTitle,apyTV,valueTV,LpTV;

        CardView card;

        Button BuyBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            PlanTitle = itemView.findViewById(R.id.PlanTitle);
            apyTV = itemView.findViewById(R.id.apyTV);
            valueTV = itemView.findViewById(R.id.valueTV);
            LpTV = itemView.findViewById(R.id.LpTV);
            BuyBtn = itemView.findViewById(R.id.BuyBtn);

            card = itemView.findViewById(R.id.card);


        }
    }
}
