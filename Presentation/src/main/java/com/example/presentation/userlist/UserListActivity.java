/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.userlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.presentation.R;
import com.example.presentation.base.view.BaseActivity;
import com.example.presentation.page.userdetails.view.UserDetailsActivity;
import com.example.presentation.page.userlist.view.UserListFragment;
import com.example.presentation.page.userlist.view.UserModel;

import org.androidannotations.annotations.EActivity;

@EActivity
public class UserListActivity extends BaseActivity implements UserListFragment.UserListListener {


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserListActivity_.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_user_list);
    }

    @Override
    public void onUserClicked(UserModel userModel) {
        Intent intentToLaunch = UserDetailsActivity.getCallingIntent(this, userModel.getUserId());
        startActivity(intentToLaunch);
    }

}
