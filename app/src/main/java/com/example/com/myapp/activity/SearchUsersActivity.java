package com.example.com.myapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import android.view.MenuItem;

import com.example.com.myapp.R;

import com.example.com.myapp.layout.FragmentShowUserInfo;
import com.example.com.myapp.layout.FragmentSpecList;
import com.example.com.myapp.layout.FragmentUserList;


public class SearchUsersActivity extends AppCompatActivity implements UserListHandler {

    FragmentUserList fragmentUserList;
    FragmentSpecList fragmentSpecList;
    FragmentShowUserInfo fragmentShowUserInfo;
    FragmentTransaction fTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
        fragmentSpecList = new FragmentSpecList();
        fragmentUserList = new FragmentUserList();
        fragmentShowUserInfo = new FragmentShowUserInfo();

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.fr_place, fragmentSpecList);
        fTrans.addToBackStack(null);
        fTrans.commit();

    }

    @Override
    public void showUserList(String spec) {
        fTrans = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("bundle_content", spec);
        fragmentUserList.setArguments(bundle);
        fTrans.replace(R.id.fr_place, fragmentUserList);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public void showUserInfo(Integer id) {
        fTrans = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("bundle_content", id);
        fragmentShowUserInfo.setArguments(bundle);
        fTrans.replace(R.id.fr_place, fragmentShowUserInfo);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}