package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btn3, L_btn;
    EditText L_email, L_pwd;
    TextView L_reg, textView3;
    String type;

    //Location
    Context context;
    int REQUEST_LOCATION = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    double distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = LoginActivity.this;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        //animation
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.linearLayout2);
        ll2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
        btn3 = (Button) findViewById(R.id.bck_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        L_reg = (TextView) findViewById(R.id.L_reg);
        L_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(i);
                finish();
            }
        });
        //Dropdown menu code
        Spinner spinner_login = findViewById(R.id.spinner);
        String arr[] = {"Librarian", "Student", "Faculty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_spinner, arr);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner_login.setAdapter(adapter);
        spinner_login.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = spinner_login.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //end
        L_btn = (Button) findViewById(R.id.L_btn);
        L_email = (EditText) findViewById(R.id.L_email);
        L_pwd = (EditText) findViewById(R.id.L_pwd);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context
                , Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            checkSettingsAndStartLocationUpdates();
        }
        L_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = L_email.getText().toString();
                String pwd = L_pwd.getText().toString();
                CircularEncryption ce = new CircularEncryption();
                UserDatabase db = new UserDatabase(getApplicationContext());
                String range = "null";
                Cursor d = db.getLocationDetails();
                while (d.moveToNext()){
                    range = d.getString(3);
                }
                if (type.length() == 0 || emailid.length() == 0 || pwd.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please fill all the details !", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.checkRecord(type, ce.circularEncryption(emailid, 3, 5), ce.circularEncryption(pwd, 3, 5))) {
                        if (type == "Librarian") {
                            Intent i = new Intent(LoginActivity.this, LibranianAct.class);
                            i.putExtra("email", emailid);
                            startActivity(i);
                            //Toast.makeText(LoginActivity.this, "Logged In Successfully !", Toast.LENGTH_SHORT).show();
                        } else if (type == "Student" || type == "Faculty") {


                            if(distance>=0&&distance<Double.parseDouble(range)){
                                Intent i = new Intent(LoginActivity.this, IssueBook.class);
                            i.putExtra("type", type);
                            i.putExtra("email", emailid);
                            startActivity(i);
                            //Toast.makeText(LoginActivity.this, "Coming Soon !", Toast.LENGTH_SHORT).show();
                            }else{
                                //Log.d("MK",Double.toString(distance));
                                Toast.makeText(context, "Please be within "+ range+"km of Library Location", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {

                        Toast.makeText(LoginActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Toast.makeText(context, "All Settings Ok", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(context, "Turn On Location !", Toast.LENGTH_SHORT).show();
                if(e instanceof ResolvableApiException){
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(LoginActivity.this,REQUEST_LOCATION);
                    } catch (IntentSender.SendIntentException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //fusedLocationProviderClient.removeLocationUpdates(this);
                double lat1 = locationResult.getLocations().get(locationResult.getLocations().size()-1).getLatitude();
                double lon1 = locationResult.getLocations().get(locationResult.getLocations().size()-1).getLongitude();
                UserDatabase db = new UserDatabase(context);
                Cursor c = db.getLocationDetails();
                double lat2 = 0;
                double lon2 = 0;
                while (c.moveToNext()){
                    lat2 = c.getDouble(0);
                    lon2 = c.getDouble(1);

                }
                double distx = haversine(lat1,lon1,lat2,lon2);
                Log.d("LAT",Double.toString(distx));
                distance = distx;
            }
        }, Looper.myLooper());
    }
    double haversine(double lat1, double lon1, double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}