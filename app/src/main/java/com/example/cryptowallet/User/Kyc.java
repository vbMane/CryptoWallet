package com.example.cryptowallet.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cryptowallet.Loading;
import com.example.cryptowallet.Login;
import com.example.cryptowallet.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

public class Kyc extends AppCompatActivity {

    private String  downloadImageUrl;
    private static final int GalleryPick = 1;
    private Uri ImageUri;

    private StorageReference ImagesRef;

    ImageView select_image;

    String name;
    String address;
    String phoneNumber;
    String dateOfBirth;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        address = intent.getStringExtra("address");
        phoneNumber = intent.getStringExtra("phone");
        dateOfBirth = intent.getStringExtra("DOB");
        password = intent.getStringExtra("pass");

        select_image = findViewById(R.id.select_image);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageUri == null){
                    Toast.makeText(Kyc.this, "Please select Pan Image.", Toast.LENGTH_SHORT).show();
                }else {
                    Loading.startLoad(Kyc.this);
                    AddPanImage();
                }
            }
        });

    }
    private String getImageExtension(Uri uri) {
        String extension = null;
        ContentResolver contentResolver = getContentResolver();


        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();


        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {

            extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        } else {

            extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        }

        return extension;
    }


    private void AddPanImage() {

        ImagesRef = FirebaseStorage.getInstance().getReference().child("PanImg");
        int randomNumberInRange = new Random().nextInt(901) + 100;

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                final StorageReference filePath = ImagesRef.child(ImageUri.getLastPathSegment() + randomNumberInRange + "."+getImageExtension(ImageUri));

                final UploadTask uploadTask = filePath.putFile(ImageUri);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = e.toString();
                        Toast.makeText(Kyc.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        Loading.stopLoad();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Toast.makeText(Kyc.this, "Pan Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful())
                                {
                                    throw task.getException();

                                }

                                downloadImageUrl = filePath.getDownloadUrl().toString();
                                return filePath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful())
                                {
                                    downloadImageUrl = task.getResult().toString();
                                    SaveInfoToDatabase();
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void SaveInfoToDatabase()
    {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("Full_Name", name);
                        userdataMap.put("phone", phoneNumber);
                        userdataMap.put("DOB", dateOfBirth);
                        userdataMap.put("address", address );
                        userdataMap.put("PanImg", downloadImageUrl );
                        userdataMap.put("Status", "NV" );
                        userdataMap.put("password", password);

                        RootRef.child("Users").child(phoneNumber).child("Profile").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Loading.stopLoad();
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    Toast.makeText(Kyc.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                } else {
                                    Loading.stopLoad();
                                    Toast.makeText(Kyc.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Kyc.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        Log.e("error",databaseError.getMessage());
                        Loading.stopLoad();
                    }
                });


            }
        });
    }

    private void OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            select_image.setImageURI(ImageUri);
        }
    }
}