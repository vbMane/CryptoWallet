package com.example.cryptowallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptowallet.User.Kyc;
import com.example.cryptowallet.User.VerifyOtp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {



    String phoneNumber;
    String Name;
    String DateOfBirth;
    String address;
    String pass;
    private TextView dob;
    EditText nameET, MobileET, addressET, PassET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        dob = findViewById(R.id.dob);
        PassET = findViewById(R.id.PassET);
        nameET = findViewById(R.id.nameET);
        MobileET = findViewById(R.id.MobileET);
        addressET = findViewById(R.id.addressET);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(dob);
            }
        });


        findViewById(R.id.RegisterBtn).setOnClickListener(v -> {
            phoneNumber = MobileET.getText().toString().trim();
            Name = nameET.getText().toString();
            DateOfBirth = dob.getText().toString();
            pass = PassET.getText().toString().trim();
            address = addressET.getText().toString();

            if (Name.isEmpty() || DateOfBirth.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "all field Required..", Toast.LENGTH_SHORT).show();
            } else if (phoneNumber.length() != 10) {
                Toast.makeText(this, "Please enter valid Mobile Number.", Toast.LENGTH_SHORT).show();
            } else {

                if (calculateAge(DateOfBirth) < 18) {
                    Toast.makeText(this, "Required age 18 +", Toast.LENGTH_SHORT).show();
                } else {

                    checkUserExits();
                }
            }


        });


    }

    private void showDatePickerDialog(TextView dateTextView) {

        Calendar selectedDate = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the selected date
                        selectedDate.set(year, month, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        dateTextView.setText(sdf.format(selectedDate.getTime()));

                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void checkUserExits() {

        Loading.startLoad(Register.this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference().child("Users");
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("error",dataSnapshot.toString());

                        if (!(dataSnapshot.child(phoneNumber).exists())){
                            Loading.stopLoad();
                            startActivity(new Intent(Register.this, Kyc.class)
                                    .putExtra("Name", Name)
                                    .putExtra("address", address)
                                    .putExtra("phone", phoneNumber)
                                    .putExtra("DOB", DateOfBirth)
                                    .putExtra("pass", pass));

                        }else {
                            Toast.makeText(Register.this, "This " + phoneNumber + " already exists.", Toast.LENGTH_SHORT).show();
                            Loading.stopLoad();
                            Toast.makeText(Register.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.e("error",databaseError.toString());

                    }
                });


            }
        });
    }


    private static int calculateAge(String inputDate) {

        Calendar dob = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dob.setTime(sdf.parse(inputDate));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (currentDate.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
}