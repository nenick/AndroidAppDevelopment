/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.userlist;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.base.presenter.Presenter;
import com.example.presentation.exception.ErrorMessageFactory;
import com.example.shared.dagger.DaggerSupport;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;


import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.BackgroundExecutor;

import java.util.Collection;

import javax.inject.Inject;

import static org.androidannotations.annotations.UiThread.Propagation.REUSE;


/**
 * {@link com.example.presentation.base.presenter.Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@EBean
public class UserListPresenter implements Presenter {

    public static final String TASK_ID_GET_USER_LIST = "cancellable_task";
    private UserListView viewListView;

    @Inject
    protected GetUserListUseCase getUserListUseCase;

    @Bean
    protected UserModelDataMapper userModelDataMapper;

    public void setView(UserListView viewListView) {
        this.viewListView = viewListView;
    }

    @AfterInject
    protected void afterInject() {
        DaggerSupport.inject(this);
    }

    public void initialize(UserListView userListFragment) {
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

    @Background(id= TASK_ID_GET_USER_LIST)
    protected void getUserList() {
        this.getUserListUseCase.execute(new GetUserListUseCase.Callback() {
            @Override
            public void success(Collection<User> usersCollection) {
                onUserListLoaded(usersCollection);
            }

            @Override
            public void failed(ErrorBundle errorBundle) {
                onError(errorBundle);
            }
        });
    }

    @UiThread(propagation = REUSE)
    protected void onUserListLoaded(Collection<User> usersCollection) {
        showUsersCollectionInView(usersCollection);
        hideViewLoading();
    }


    @UiThread(propagation = REUSE)
    protected void onError(ErrorBundle errorBundle) {
        hideViewLoading();
        showErrorMessage(errorBundle);
        showViewRetry();
    }

    public void onViewDestroyed() {
        BackgroundExecutor.cancelAll(TASK_ID_GET_USER_LIST, true);
    }
}
