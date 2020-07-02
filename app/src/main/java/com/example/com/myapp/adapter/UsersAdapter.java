package com.example.com.myapp.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.example.com.myapp.R;
import com.example.com.myapp.pojo.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    public UsersAdapter(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    public void setItems(Collection<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItems() {
        userList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImageView;
        private TextView nameTextView;
        private TextView birthdayTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.profile_image_view);
            nameTextView = itemView.findViewById(R.id.user_full_name_text_view);
            birthdayTextView = itemView.findViewById(R.id.user_birthday_person_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = userList.get(getLayoutPosition());
                    onUserClickListener.onUserClick(user);
                }
            });
        }

        public void bind(User user) {
            nameTextView.setText(user.getFirstName() + " " + user.getLastName());
            birthdayTextView.setText(user.getBirthday());
            if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
                Picasso.with(itemView.getContext()).load(user.getImageUrl()).into(userImageView);
            } else {
                Picasso.with(itemView.getContext()).load("@mipmap/ic_launcher").into(userImageView);
            }
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
}