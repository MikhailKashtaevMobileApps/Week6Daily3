package com.example.mike.week6daily3.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.mike.week6daily3.GeofenceTransitionsIntentService;
import com.example.mike.week6daily3.R;
import com.example.mike.week6daily3.data.Location;
import com.example.mike.week6daily3.data.repository.DataCallback;
import com.example.mike.week6daily3.utils.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;

    public static final String TAG = "__TAG__";
    private RecyclerView rvLocationList;
    private LocationsAdapter locationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        rvLocationList = findViewById( R.id.rvLocationList );
        locationsAdapter = new LocationsAdapter(new ArrayList<Location>());
        rvLocationList.setAdapter(locationsAdapter);
        rvLocationList.setLayoutManager(new LinearLayoutManager(this));

        displayLocations();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1 );
                return;
            }
        }
        createGeofences();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            // Go ahead motherfucker
            createGeofences();
        }
    }

    @SuppressLint("MissingPermission")
    public void createGeofences(){
        mainViewModel.getGeoFences();
    }

    public void displayLocations(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainViewModel.getLocations(new DataCallback() {
                    @Override
                    public void onSuccess(List<Location> locations) {
                        Log.d(TAG, "onSuccess: location size="+locations.toString());
                        locationsAdapter.locations = locations;
                        locationsAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Refreshed Locations", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Log.d(TAG, "onError: "+error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 0, 10000);
    }
}
