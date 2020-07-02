package com.example.com.myapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.com.myapp.R;


public class SpecAdapter extends RecyclerView.Adapter<SpecAdapter.UserViewHolder> {
    private List<String> userList = new ArrayList<>();
    private OnUserClickListener onUserClickListener;


    public SpecAdapter(OnUserClickListener onUserClickListener) {
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
        String string = userList.get(position);
        holder.bind(string);
    }

    public void setItems(Collection<String> users) {
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

        private TextView nameTextView;
        private TextView birthdayTextView;
        private ImageView userImageView;

        public UserViewHolder(final View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.user_full_name_text_view);
            birthdayTextView = itemView.findViewById(R.id.user_birthday_person_text_view);
            userImageView = itemView.findViewById(R.id.profile_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String line = userList.get(getLayoutPosition());
                    onUserClickListener.onUserClick(line);
                }
            });
        }

        public void bind(String line) {
            nameTextView.setText(line.substring(1, line.length() - 1));
            birthdayTextView.setText("");
            userImageView.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnUserClickListener {
        void onUserClick(String line);
    }
}