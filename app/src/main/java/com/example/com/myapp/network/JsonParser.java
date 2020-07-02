package com.example.com.myapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import com.example.com.myapp.pojo.Response;

public class JsonParser {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    public Response getUsers(String response) {
        Type usersType = new TypeToken<Response>() {
        }.getType();
        return gson.fromJson(response, usersType);
    }

}