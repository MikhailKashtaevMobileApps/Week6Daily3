package com.example.mike.week6daily3.data.repository.LocalDataSource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.mike.week6daily3.data.Location;

import java.util.List;


/*
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
*/
@Dao
public interface LocationDAO {

    @Query("SELECT * FROM location ORDER BY timestamp")
    List<Location> getLocations();

    @Insert()
    void insert(Location location);



}
