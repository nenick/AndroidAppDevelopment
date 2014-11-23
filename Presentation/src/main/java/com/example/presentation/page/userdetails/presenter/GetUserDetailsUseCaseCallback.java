package com.example.presentation.page.userdetails.presenter;

import com.example.domain.interactor.GetUserDetailsUseCase;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import static org.androidannotations.annotations.UiThread.Propagation.REUSE;

@EBean
class GetUserDetailsUseCaseCallback implements GetUserDetailsUseCase.Callback {

    private UserDetailsPresenter presenter;

    public void register(UserDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    @UiThread(propagation = REUSE)
    public void onUserDataLoaded(User user) {
        presenter.showUserDetailsInView(user);
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