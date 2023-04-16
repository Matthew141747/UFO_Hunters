package com.example.ufo_hunters;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profilec extends AppCompatActivity {

    Button buttonHome, buttonCommunity, buttonProfile, buttonUpdate, buttonLogout;
    TextView fullName, email, DOB;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore store;

    String userID;

    //String fullName, email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilec);

        fullName = findViewById(R.id.textView10);
        email = findViewById(R.id.textView7);
        DOB = findViewById(R.id.textView2);
        buttonLogout = findViewById(R.id.button8);
        buttonHome = findViewById(R.id.buttonHC);
        //buttonCommunity = findViewById(R.id.);
        buttonUpdate = findViewById(R.id.button);
       // buttonHome = findViewById(R.id.);

        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        user = auth.getCurrentUser();

       // if(auth.getCurrentUser() !=null) {
            userID = auth.getCurrentUser().getUid();
       // }
        DocumentReference documentReference = store.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fullName.setText(documentSnapshot.getString("FullName"));
                email.setText(documentSnapshot.getString("email"));
                DOB.setText(documentSnapshot.getString("DOB"));
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), updateProfile.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}