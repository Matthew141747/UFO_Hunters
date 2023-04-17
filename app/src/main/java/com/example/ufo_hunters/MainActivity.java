package com.example.ufo_hunters;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity {
//implements OnMapReadyCallback
    FirebaseAuth auth;
    Button button, buttonB, buttonComunity;
    TextView textView;
    FirebaseUser user;

    GoogleMap myMap;

    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        buttonB = findViewById(R.id.Profile);
        textView = findViewById(R.id.user_details);
        buttonComunity = findViewById(R.id.Community);

        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), profilec.class);
                startActivity(intent);
                finish();
            }
        });

        buttonComunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Community.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        /**

     SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
     mapFragment.getMapAsync(this);
         **/
    }

   // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
   // getLastLocation();

/**
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        myMap = googleMap;
        LatLng dublin = new LatLng(53, -6);
        myMap.addMarker(new MarkerOptions().position(dublin).title("dublin"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(dublin));

    }
    /**

    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3'

    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
    implementation 'androidx.activity:activity-compose:1.5.1'
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3'
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    debugImplementation 'androidx.compose.ui:ui-tooling'
    debugImplementation 'androidx.compose.ui:ui-test-manifest'


    **/
}