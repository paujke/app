package com.example.com.myapp;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.com.myapp.db.AppDatabase;

public class App extends Application {
    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class, "user-database")
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
