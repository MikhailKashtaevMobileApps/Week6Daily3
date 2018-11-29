package com.example.mike.week6daily3.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.mike.week6daily3.utils.Constants;

import java.util.Date;

@Entity
public class Location {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;

    public Double lat;
    public Double lng;
    public String name;
    public Long timestamp;
    public String event;

    public Location(Double lat, Double lng, String name) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        timestamp = new Date().getTime();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                ", event='" + event + '\'' +
                '}';
    }

    public static Location locationByName(String name ) throws Exception {
        for (Location location : Constants.LOCATIONS) {
            if (location.getName().equals(name)){
                return new Location( location.getLat(), location.getLng(), location.getName() );
            }
        }
        throw new Exception("No such location is registered");
    }
}




