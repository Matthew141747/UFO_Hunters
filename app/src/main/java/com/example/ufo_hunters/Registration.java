package com.example.ufo_hunters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {


    TextInputEditText editTextEmail, editTextPassword,editTextPasswordB, editTextFullName;
    Button buttonRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView textView;
    ProgressBar progressBar;
    private Button buttonToLogin;
    private TextView SelectDate;
    private static final String TAG = "TAG";

    //private EditText SelectDate;

    private DatePickerDialog.OnDateSetListener DateSetListener;
    String userID;

    public boolean checkPassword(String a, String b){
        if(a.equals(b)){
            return true;
        }else{
            Toast.makeText(Registration.this,"Passwords do not match" ,Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        editTextEmail =findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPasswordB = findViewById(R.id.passwordConfirm);
        editTextFullName = findViewById(R.id.FullName);
        buttonRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        buttonToLogin = (Button) findViewById(R.id.changeToLogin);

        SelectDate = (TextView) findViewById(R.id.edit_date);

        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(Registration.this,
                            android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, DateSetListener, year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });


        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = month + "/" + day + "/" + year;
                SelectDate.setText(date);
            }
        };


        buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password, passwordB, fullName, DOB;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                passwordB = String.valueOf(editTextPasswordB.getText());
                fullName = String.valueOf((editTextFullName.getText()));
                DOB =  SelectDate.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordB)) {
                    Toast.makeText(Registration.this, "Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkPassword(password, passwordB)) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registration.this, "Account Created",
                                                Toast.LENGTH_SHORT).show();

                                        userID = mAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fStore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("FullName", fullName);
                                        user.put("email", email);
                                        user.put("DOB", DOB);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "onSuccess: User Profile Is Created for " + userID);
                                            }
                                        });

                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        Toast.makeText(Registration.this, "Account Creation failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}