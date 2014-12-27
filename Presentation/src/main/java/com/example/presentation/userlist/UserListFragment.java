/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.userlist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.presentation.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.Collection;

/**
 * Fragment that shows a list of Users.
 */
@EFragment
public class UserListFragment extends Fragment implements UserListView {

    /**
     * Interface for listening user list events.
     */
    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }

    @Bean
    protected UserListPresenter userListPresenter;

    private ListView lv_users;
    private RelativeLayout rl_progress;
    private RelativeLayout rl_retry;
    private Button bt_retry;

    private UsersAdapter usersAdapter;

    private UserListListener userListListener;

    public UserListFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof UserListListener) {
            this.userListListener = (UserListListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, true);

        this.lv_users = (ListView) fragmentView.findViewById(R.id.lv_users);
        this.lv_users.setOnItemClickListener(this.userOnItemClickListener);
        this.rl_progress = (RelativeLayout) fragmentView.findViewById(R.id.rl_progress);
        this.rl_retry = (RelativeLayout) fragmentView.findViewById(R.id.rl_retry);
        this.bt_retry = (Button) fragmentView.findViewById(R.id.bt_retry);
        this.bt_retry.setOnClickListener(this.retryOnClickListener);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.userListPresenter.initialize(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userListPresenter.onViewDestroyed();
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
    public void renderUserList(Collection<UserModel> userModelCollection) {
        if (userModelCollection != null) {
            if (this.usersAdapter == null) {
                this.usersAdapter = new UsersAdapter(getActivity(), userModelCollection);
            } else {
                this.usersAdapter.setUsersCollection(userModelCollection);
            }
            this.lv_users.setAdapter(usersAdapter);
        }
    }

    @Override
    public void viewUser(UserModel userModel) {
        if (this.userListListener != null) {
            this.userListListener.onUserClicked(userModel);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        if (this.userListPresenter != null) {
            this.userListPresenter.initialize(this);
        }
    }

    /**
     * Views a {@link UserModel} when is clicked.
     * Uses the presenter via composition to achieve this.
     *
     * @param userModel {@link UserModel} to show.
     */
    private void onUserClicked(UserModel userModel) {
        if (this.userListPresenter != null) {
            this.userListPresenter.onUserClicked(userModel);
        }
    }

    private final View.OnClickListener retryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserListFragment.this.loadUserList();
        }
    };

    private final AdapterView.OnItemClickListener userOnItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UserModel userModel = (UserModel) UserListFragment.this.usersAdapter.getItem(position);
                    UserListFragment.this.onUserClicked(userModel);
                }
            };
}
