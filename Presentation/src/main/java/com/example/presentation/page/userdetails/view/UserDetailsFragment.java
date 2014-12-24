/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.page.userdetails.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.presentation.R;
import com.example.presentation.base.view.BaseFragment;
import com.example.presentation.page.userdetails.presenter.UserDetailsPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

/**
 * Fragment that shows details of a certain user.
 */
@EFragment
public class UserDetailsFragment extends BaseFragment implements UserDetailsView {

    private static final String ARGUMENT_KEY_USER_ID = "org.android10.ARGUMENT_USER_ID";

    @FragmentArg
    protected int userId;

    @Bean
    protected UserDetailsPresenter userDetailsPresenter;

    private AutoLoadImageView iv_cover;
    private TextView tv_fullname;
    private TextView tv_email;
    private TextView tv_followers;
    private TextView tv_description;
    private RelativeLayout rl_progress;
    private RelativeLayout rl_retry;
    private Button bt_retry;

    public UserDetailsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);

        this.iv_cover = (AutoLoadImageView) fragmentView.findViewById(R.id.iv_cover);
        this.tv_fullname = (TextView) fragmentView.findViewById(R.id.tv_fullname);
        this.tv_email = (TextView) fragmentView.findViewById(R.id.tv_email);
        this.tv_followers = (TextView) fragmentView.findViewById(R.id.tv_followers);
        this.tv_description = (TextView) fragmentView.findViewById(R.id.tv_description);
        this.rl_progress = (RelativeLayout) fragmentView.findViewById(R.id.rl_progress);
        this.rl_retry = (RelativeLayout) fragmentView.findViewById(R.id.rl_retry);
        this.bt_retry = (Button) fragmentView.findViewById(R.id.bt_retry);
        this.bt_retry.setOnClickListener(this.retryOnClickListener);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializePresenter();
    }

    @Override
    protected void initializePresenter() {
        this.userDetailsPresenter.initialize(this, this.userId);
    }

    @Override
    public void renderUser(UserModel user) {
        if (user != null) {
            this.iv_cover.setImageUrl(user.getCoverUrl());
            this.tv_fullname.setText(user.getFullName());
            this.tv_email.setText(user.getEmail());
            this.tv_followers.setText(String.valueOf(user.getFollowers()));
            this.tv_description.setText(user.getDescription());
        }
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads all users.
     */
    private void loadUserDetails() {
        if (this.userDetailsPresenter != null) {
            this.userDetailsPresenter.initialize(this, this.userId);
        }
    }

    private final View.OnClickListener retryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserDetailsFragment.this.loadUserDetails();
        }
    };
}
