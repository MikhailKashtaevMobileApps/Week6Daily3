package com.example.mike.week6daily3.data.repository;

import com.example.mike.week6daily3.data.Location;

import java.util.List;

public interface DataCallback {

    void onSuccess(List<Location> locations);
    void onError(String error);

}
