package com.example.mike.week6daily3;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mike.week6daily3.data.Location;
import com.example.mike.week6daily3.data.repository.LocationRepository;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Iterator;
import java.util.List;

public class GeofenceTransitionsIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String TAG = "__TAG__";

    LocationRepository locationRepository;

    public GeofenceTransitionsIntentService() {
        super("GEOFENCEINTENTSERVICE");
        locationRepository = new LocationRepository(this);
    }

    public GeofenceTransitionsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: received intent");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onHandleIntent: failed with error code="+geofencingEvent.getErrorCode());
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            for (Geofence triggeringGeofence : triggeringGeofences) {
                try {
                    Location location = Location.locationByName( triggeringGeofence.getRequestId() );
                    String event;
                    if ( geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ){
                        event = "enter";
                    }else{
                        event = "exit";
                    }
                    location.setEvent( event );
                    locationRepository.insertLocation(location);
                    Log.d(TAG, "onHandleIntent: Saving Location:"+location.toString());
                } catch (Exception e) {
                    Log.d(TAG, "onHandleIntent: Error="+e.getMessage());
                }
            }


        } else {
            // Log the error.
            Log.e(TAG, "Invalid transition");
        }

    }
}
