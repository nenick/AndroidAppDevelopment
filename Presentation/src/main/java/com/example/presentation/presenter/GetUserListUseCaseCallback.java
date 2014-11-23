package com.example.presentation.presenter;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.Collection;

import static org.androidannotations.annotations.UiThread.Propagation.REUSE;

@EBean
class GetUserListUseCaseCallback implements GetUserListUseCase.Callback {

    private UserListPresenter presenter;

    public void register(UserListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    @UiThread(propagation = REUSE)
    public void onUserListLoaded(Collection<User> usersCollection) {
        presenter.showUsersCollectionInView(usersCollection);
        presenter.hideViewLoading();
    }

    @Override
    @UiThread(propagation = REUSE)
    public void onError(ErrorBundle errorBundle) {
        presenter.hideViewLoading();
        presenter.showErrorMessage(errorBundle);
        presenter.showViewRetry();
    }
}