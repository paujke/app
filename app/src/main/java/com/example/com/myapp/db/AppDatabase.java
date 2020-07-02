package com.example.com.myapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.com.myapp.pojo.User;

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

}
