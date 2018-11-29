package com.example.mike.week6daily3.data.repository.LocalDataSource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.mike.week6daily3.data.Location;


@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDAO locationDAO();
}
