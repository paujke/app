package com.example.com.myapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import com.example.com.myapp.pojo.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User WHERE speciality Like :spec")
    List<User> getSpecUsers(String spec);

    @Query("SELECT DISTINCT speciality FROM User")
    List<String> getSpecList();

    @Query("SELECT * FROM User where id = :id")
    List<User> getUser(int id);

    @Query("DELETE FROM User")
    void delete();
}
