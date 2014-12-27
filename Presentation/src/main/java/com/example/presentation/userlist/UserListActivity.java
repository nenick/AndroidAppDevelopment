package com.example.presentation.userlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.presentation.base.view.BaseActivity;
import com.example.presentation.page.userdetails.view.UserDetailsActivity;

import org.androidannotations.annotations.EActivity;

@EActivity(resName = "activity_user_list")
public class UserListActivity extends BaseActivity implements UserListFragment.UserListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    @Override
    public void onUserClicked(UserModel userModel) {
        Intent intentToLaunch = UserDetailsActivity.getCallingIntent(this, userModel.getUserId());
        startActivity(intentToLaunch);
    }
}