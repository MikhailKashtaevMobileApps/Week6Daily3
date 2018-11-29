package com.example.mike.week6daily3.data.repository.LocalDataSource;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.example.mike.week6daily3.data.Location;
import com.example.mike.week6daily3.data.repository.DataCallback;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource {

    Context context;
    public static final String TAG = "__TAG__";

    public LocalDataSource(Context context) {
        this.context = context;
    }

    private LocationDAO getDao(){
        return Room.databaseBuilder(context, LocationDatabase.class, "locationdb").build().locationDAO();
    }

    public void getLocations(final DataCallback dataCallback){
        Single.fromCallable(new Callable<List<Location>>() {
            @Override
            public List<Location> call() {
                return getDao().getLocations();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Location>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Location> locations) {
                        Log.d(TAG, "onSuccess: numLocations="+locations.size());
                        dataCallback.onSuccess( locations );
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataCallback.onError(e.getMessage());
                    }
                });
    }

    public void insertLocation(final Location location){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                getDao().insert(location);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
