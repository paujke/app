package com.example.com.myapp.layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.example.com.myapp.App;
import com.example.com.myapp.R;
import com.example.com.myapp.activity.UserListHandler;
import com.example.com.myapp.adapter.UsersAdapter;
import com.example.com.myapp.db.AppDatabase;
import com.example.com.myapp.pojo.User;


public class FragmentUserList extends Fragment {


    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private AppDatabase appDatabase = App.getAppDatabase();

    private static final String BUNDLE_CONTENT = "bundle_content";

    private String content;

    public FragmentUserList() {
        //default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            content = getArguments().getString(BUNDLE_CONTENT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        usersRecyclerView = view.findViewById(R.id.users_recycler_view);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        UsersAdapter.OnUserClickListener onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                ((UserListHandler) getActivity()).showUserInfo(user.getId());
            }
        };

        usersAdapter = new UsersAdapter(onUserClickListener);
        usersRecyclerView.setAdapter(usersAdapter);
        loadUser(content);
        return view;
    }

    private void loadUser(final String content) {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                return appDatabase.getUserDao().getSpecUsers("%" + content.substring(1, content.length() - 1) + "%");
            }

            @Override
            protected void onPostExecute(List<User> users) {
                usersAdapter.clearItems();
                usersAdapter.setItems(users);
            }
        }.execute();
    }
}