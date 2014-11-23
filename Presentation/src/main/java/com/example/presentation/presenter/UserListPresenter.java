/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.presenter;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.exception.ErrorMessageFactory;
import com.example.presentation.mapper.UserModelDataMapper;
import com.example.presentation.model.UserModel;
import com.example.presentation.view.UserListView;
import com.example.shared.dagger.DaggerSupport;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;


import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Collection;

import javax.inject.Inject;


/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@EBean
public class UserListPresenter implements Presenter {

    private UserListView viewListView;

    @Inject
    protected GetUserListUseCase getUserListUseCase;

    @Bean
    protected GetUserListUseCaseCallback userListCallback;

    @Bean
    protected UserModelDataMapper userModelDataMapper;

    public void setView(UserListView viewListView) {
        this.viewListView = viewListView;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     * @param userListFragment
     */
    public void initialize(UserListView userListFragment) {
        DaggerSupport.inject(this);
        userListCallback.register(this);
        viewListView = userListFragment;
        this.loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    public void onUserClicked(UserModel userModel) {
        this.viewListView.viewUser(userModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
                errorBundle.getError());
        this.viewListView.showError(errorMessage);
    }

    void showUsersCollectionInView(Collection<User> usersCollection) {
        final Collection<UserModel> userModelsCollection =
                this.userModelDataMapper.transform(usersCollection);
        this.viewListView.renderUserList(userModelsCollection);
    }

    @Background
    protected void getUserList() {
        this.getUserListUseCase.execute(userListCallback);
    }
}
