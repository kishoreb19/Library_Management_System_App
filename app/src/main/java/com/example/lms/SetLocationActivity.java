package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.io.IOException;
import java.util.List;

public class SetLocationActivity extends AppCompatActivity {
    Button bck_btn, btn_set_location;
    Context context;
    //Location
    int REQUEST_LOCATION = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    TextView lib_address,lib_latitude,lib_longitude;
    EditText range;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        context =  SetLocationActivity.this;
        bck_btn = (Button)findViewById(R.id.bck_btn);
        range = (EditText) findViewById(R.id.range);
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lib_address  = (TextView)findViewById(R.id.lib_address);
        lib_latitude  = (TextView)findViewById(R.id.lib_latitude);
        lib_longitude  = (TextView)findViewById(R.id.lib_longitude);
        UserDatabase db = new UserDatabase(context);
        Cursor cursor = db.getLocationDetails();
        while(cursor.moveToNext()){
            lib_address.setText(cursor.getString(2));
            lib_latitude.setText(cursor.getString(0));
            lib_longitude.setText(cursor.getString(1));
            range.setText(cursor.getString(3));
        }
        btn_set_location = (Button) findViewById(R.id.btn_set_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        btn_set_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context
                        , Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    checkSettingsAndStartLocationUpdates();
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
                        resolvableApiException.startResolutionForResult(SetLocationActivity.this,REQUEST_LOCATION);
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
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                fusedLocationProviderClient.removeLocationUpdates(this);
                Location i = locationResult.getLocations().get(locationResult.getLocations().size()-1);

                UserDatabase db = new UserDatabase(context);
                double latitude = i.getLatitude();
                double longitude = i.getLongitude();
                Geocoder geocoder = new Geocoder(context);
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(latitude,longitude,1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String address = addressList.get(0).getAddressLine(0);

                String range_d = range.getText().toString();
                if(db.getLocationDetails().getCount()==0){
                    long x = db.insertLocation(latitude,longitude,address,range_d);
                    if(x!=-1){
                        Toast.makeText(context, "Location Added", Toast.LENGTH_SHORT).show();
                        lib_address.setText(address);
                        lib_latitude.setText(Double.toString(latitude));
                        lib_longitude.setText(Double.toString(longitude));
                    }else{
                        Toast.makeText(context, "Error in Adding Location", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    long x = db.updateLocationDetails(latitude,longitude,address,range_d);

                    if(x!=-1){
                        Toast.makeText(context, "Update Success!", Toast.LENGTH_SHORT).show();
                        Cursor cursor = db.getLocationDetails();
                        while(cursor.moveToNext()){
                            lib_address.setText(cursor.getString(2));
                            lib_latitude.setText(cursor.getString(0));
                            lib_longitude.setText(cursor.getString(1));
                        }

                    }else{
                        Toast.makeText(context, "Update Error", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, Looper.getMainLooper());
    }
}
