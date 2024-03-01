package com.example.cryptowallet.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.cryptowallet.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewUserData extends AppCompatActivity {

    DatabaseReference databaseReference;


    private Button rejectButton, acceptButton;
    private ImageView imageView,adIMG;
    private TextView nameTextView, mobileTextView, addressTextView, dobTextView,UpiIDTV;

    ProgressBar progressBar,progressBarAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_data);


        rejectButton = findViewById(R.id.rejectBtn);
        acceptButton = findViewById(R.id.Accept);
        imageView = findViewById(R.id.imageView2);
        nameTextView = findViewById(R.id.textViewName);
        mobileTextView = findViewById(R.id.textViewMobile);
        addressTextView = findViewById(R.id.textViewAddress);
        dobTextView = findViewById(R.id.textViewDOB);
        UpiIDTV = findViewById(R.id.textViewUpiID);
        adIMG = findViewById(R.id.adIMG);
        progressBarAD = findViewById(R.id.progressBarAD);
        progressBar = findViewById(R.id.progressBar);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        String name = getIntent().getStringExtra("name");
        String mobile = getIntent().getStringExtra("mobile");
        String address = getIntent().getStringExtra("address");
        String dob = getIntent().getStringExtra("dob");
        String imgUrl = getIntent().getStringExtra("imgUrl");
        String ADimgUrl = getIntent().getStringExtra("ADimgUrl");
        String UpiID = getIntent().getStringExtra("UpiID");

        nameTextView.setText("Name : "+name);
        mobileTextView.setText("Mobile Number : "+mobile);
        addressTextView.setText("Address : "+address);
        dobTextView.setText("Date of Birth : "+dob);
        UpiIDTV.setText("UpiID : "+UpiID);


        Glide.with(this)
                .load(imgUrl)
                .error(R.drawable.applogo)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        Glide.with(this)
                .load(ADimgUrl)
                .error(R.drawable.applogo)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBarAD.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBarAD.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(adIMG);



    }
}