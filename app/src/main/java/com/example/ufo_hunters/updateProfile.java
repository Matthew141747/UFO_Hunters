package com.example.ufo_hunters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class updateProfile extends AppCompatActivity {
    Button uploadPhoto, update, returnToProfile;
    ImageView profilePhoto;
    Uri imageUri;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore store;
    String userID;
    StorageReference storageReference;

    //StorageReference s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        uploadPhoto = findViewById(R.id.button9);
        profilePhoto = findViewById(R.id.imageView3);
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        //storageReference = Storage.getInstance();

        update = findViewById(R.id.button6);
        returnToProfile = findViewById(R.id.button7);


        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });


    }

    private void uploadImage(){

        storageReference = FirebaseStorage.getInstance().getReference("images/user_profile/photos"+userID);


        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void getImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && data != null){

            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);


        }
    }
}