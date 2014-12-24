package com.example.app.dagger;

import android.content.Context;

import com.example.data.repository.UserDataRepository_;
import com.example.data.repository.UserRepository;
import com.example.domain.interactor.GetUserDetailsUseCaseImpl;
import com.example.domain.interactor.GetUserListUseCaseImpl;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {GetUserListUseCaseImpl.class, GetUserDetailsUseCaseImpl.class},
        complete = false
)
public class DataModule {

    @Provides
    public UserRepository provideUserRepository(@ForApplication Context context) {
        return UserDataRepository_.getInstance_(context);
    }
}
