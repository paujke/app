package com.example.com.myapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;


import org.json.JSONException;

import com.example.com.myapp.pojo.User;

public class HttpClient {

    private final JsonParser jsonParser;

    public HttpClient() {
        jsonParser = new JsonParser();
    }

    public Collection<User> readUsers() throws IOException, JSONException {
        String requestUrl = "https://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
        String encodedUrl = requestUrl.replaceAll(" ", "%20");
        String response = getResponse(encodedUrl);
        Collection<User> users = jsonParser.getUsers(response).getUser();
        return users;
    }


    private String getResponse(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.connect();

        InputStream in;
        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            in = connection.getErrorStream();
        } else {
            in = connection.getInputStream();
        }

        return convertStreamToString(in);
    }


    private String convertStreamToString(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();

        return sb.toString();
    }

}