package com.example.mike.week6daily3.data.repository;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.mike.week6daily3.data.repository.LocalDataSource.LocalDataSource;

public class LocationRepository extends LocalDataSource {

    public LocationRepository(Context context) {
        super(context);
    }

}
