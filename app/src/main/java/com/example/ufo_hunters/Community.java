package com.example.ufo_hunters;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Community extends AppCompatActivity {

    Button buttonA, buttonB, buttonHC, buttonSearch;
    EditText mSearchField;
    TextView ResultTest;
    RecyclerView mResultList;
    private static final String TAG = "search";
   // CollectionReference usersRef;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersAccountsRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_community);

        mSearchField = findViewById(R.id.searchForUser);
        mResultList = findViewById(R.id.resultlist);

        mResultList.setLayoutManager(new LinearLayoutManager(this));


        List<userObj> usrs = new ArrayList<userObj>();

        mResultList.setAdapter(new searchAdapter(getApplicationContext(), usrs));

        buttonSearch = findViewById(R.id.button13);
        buttonHC = findViewById(R.id.buttonHC);
        buttonB = findViewById(R.id.button3);

      //  List<result> result = new ArrayList<>()

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //usrs.clear();

                String userSearchInput = String.valueOf(mSearchField.getText());

                if (TextUtils.isEmpty(userSearchInput)) {
                    Toast.makeText(Community.this, "Provide Search input", Toast.LENGTH_SHORT).show();
                    return;
                }

               // if (userSearchInput != "") {
                    usersAccountsRef.whereEqualTo("FullName", userSearchInput)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    //String data = "";

                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        userObj userObj = documentSnapshot.toObject(userObj.class);
                                        userObj.setDocumentId(documentSnapshot.getId());

                                       // String documentid = userObj.getDocumentId();
                                        String FullName = userObj.getFullName();
                                        String email = userObj.getEmail();
                                        usrs.add(new userObj(FullName, email));
                                       // data += "ID: " + documentid + "\nFullName:" + FullName + "\nemail:" + email;

                                    }
                                    usrs.add(new userObj());
                                    //ResultTest.setText(data);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            });


                //}
            }
        });


        buttonHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profilec.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

