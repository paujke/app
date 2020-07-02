package com.example.com.myapp.layout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.example.com.myapp.App;
import com.example.com.myapp.R;
import com.example.com.myapp.activity.UserListHandler;
import com.example.com.myapp.adapter.SpecAdapter;
import com.example.com.myapp.db.AppDatabase;
import com.example.com.myapp.network.HttpClient;
import com.example.com.myapp.pojo.User;

public class FragmentSpecList extends Fragment {
    private RecyclerView usersRecyclerView;
    private SpecAdapter specAdapter;
    private HttpClient httpClient;
    private AppDatabase appDatabase = App.getAppDatabase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        usersRecyclerView = view.findViewById(R.id.users_recycler_view);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        SpecAdapter.OnUserClickListener onUserClickListener = new SpecAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(String line) {

                ((UserListHandler) getActivity()).showUserList(line);

            }
        };
        specAdapter = new SpecAdapter(onUserClickListener);
        usersRecyclerView.setAdapter(specAdapter);
        httpClient = new HttpClient();


        httpClient = new HttpClient();

        searchUsers();
        return view;
    }

    private void loadUser() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return appDatabase.getUserDao().getSpecList();
            }


            @Override
            protected void onPostExecute(List<String> users) {
                specAdapter.clearItems();
                specAdapter.setItems(users);
            }
        }.execute();
    }

    private void saveToDatabase(final Collection<User> users) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                for (User user : users)
                    appDatabase.getUserDao().insert(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                loadUser();
            }
        }.execute();
    }

    private void deleteDatabase() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getUserDao().delete();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void searchUsers() {
        new UsersAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UsersAsyncTask extends AsyncTask<String, Integer, Collection<User>> {

        @Override
        protected Collection<User> doInBackground(String... params) {

            try {
                return httpClient.readUsers();
            } catch (IOException | JSONException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Collection<User> users) {
            // успешный ответ
            if (users != null) {
                deleteDatabase();
                saveToDatabase(users);
            }

        }
    }

}