package com.example.com.myapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("response")
    @Expose
    private List<User> user;

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }
}
