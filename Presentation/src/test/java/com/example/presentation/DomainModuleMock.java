package com.example.presentation;

import com.example.domain.interactor.GetUserDetailsUseCase;
import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.page.userdetails.presenter.UserDetailsPresenter_;
import com.example.presentation.userlist.UserListPresenter_;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
        injects = {UserDetailsPresenter_.class, UserListPresenter_.class},
        complete = false
)
public class DomainModuleMock {

    public GetUserDetailsUseCase getUserDetailsUseCase;
    public GetUserListUseCase getUserListUseCase;

    @Provides
    public GetUserDetailsUseCase provideGetUserDetailsUseCase() {
        return getUserDetailsUseCase = mock(GetUserDetailsUseCase.class);
    }

    @Provides
    public GetUserListUseCase provideGetUserListUseCase() {
        return getUserListUseCase = mock(GetUserListUseCase.class);
    }
}
