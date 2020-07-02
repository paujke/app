package com.example.com.myapp.layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.com.myapp.App;
import com.example.com.myapp.R;
import com.example.com.myapp.db.AppDatabase;
import com.example.com.myapp.pojo.User;


public class FragmentShowUserInfo extends Fragment {
    private ImageView userImageView;
    private TextView nameTextView;
    private TextView lastNameTextView;
    private TextView birthdayTextView;
    private TextView specialityTextView;
    private AppDatabase appDatabase = App.getAppDatabase();

    private static final String BUNDLE_CONTENT = "bundle_content";

    private int content;

    public FragmentShowUserInfo() {
        //default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            content = getArguments().getInt(BUNDLE_CONTENT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_user_info, container, false);
        userImageView = view.findViewById(R.id.user_image_view);
        nameTextView = view.findViewById(R.id.user_f_name_text_view);
        lastNameTextView = view.findViewById(R.id.user_l_name_text_view);
        birthdayTextView = view.findViewById(R.id.user_birthday_text_view);
        specialityTextView = view.findViewById(R.id.user_specialty_text_view);


        loadUser(content);
        return view;
    }

    private void loadUser(final int id) {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                return appDatabase.getUserDao().getUser(id);
            }

            @Override
            protected void onPostExecute(List<User> users) {
                displayUserInfo(users.get(0));
            }
        }.execute();
    }

    private void displayUserInfo(User user) {
        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            Picasso.with(getContext()).load(user.getImageUrl()).into(userImageView);
        }
        nameTextView.setText(user.getFirstName());
        lastNameTextView.setText(user.getLastName());
        birthdayTextView.setText(user.getBirthday());
        StringBuilder stringBuilder = new StringBuilder();
        for (User.Speciality spec : user.getSpeciality()) {
            stringBuilder.append(spec);
        }
        specialityTextView.setText(stringBuilder.toString());
    }

}