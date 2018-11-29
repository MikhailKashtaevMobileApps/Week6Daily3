package com.example.mike.week6daily3.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.PendingIntent;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.mike.week6daily3.GeofenceTransitionsIntentService;
import com.example.mike.week6daily3.data.Location;
import com.example.mike.week6daily3.data.repository.DataCallback;
import com.example.mike.week6daily3.data.repository.LocationRepository;
import com.example.mike.week6daily3.utils.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MainViewModel extends AndroidViewModel {

    public static final String TAG = "__TAG__";
    Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    @SuppressLint("MissingPermission")
    public void getGeoFences( ){
        GeofencingClient geofencingClient = new GeofencingClient(context);

        // Add an array of lats and longs
        ArrayList<Geofence> geofences = new ArrayList<>();
        for (int i = 0; i < Constants.LOCATIONS.length; i++) {
            geofences.add( new Geofence.Builder()
                    .setRequestId(Constants.LOCATIONS[i].getName())
                    .setCircularRegion(Constants.LOCATIONS[i].getLat(), Constants.LOCATIONS[i].getLng(), 500)
                    .setExpirationDuration(50000000)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build() );
        }

        //Geofencing request
        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                .addGeofences(geofences)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();

        // Pending Intent
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        PendingIntent geoPendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        geofencingClient.addGeofences( geofencingRequest, geoPendingIntent )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Fencing set", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.getMessage());
                    }
                });

    }

    public void getLocations(DataCallback dataCallback){
        LocationRepository locationRepository = new LocationRepository(context);
        locationRepository.getLocations(dataCallback);
    }

}
