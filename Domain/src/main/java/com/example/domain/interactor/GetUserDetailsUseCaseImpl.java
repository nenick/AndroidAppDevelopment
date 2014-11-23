/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.domain.interactor;

import com.example.data.repository.UserRepository;
import com.example.shared.dagger.DaggerSupport;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetUserDetailsUseCase} that represents a use case for
 * retrieving data related to an specific {@link User}.
 */

public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {

    @Inject
    protected UserRepository userRepository;

    private int userId = -1;
    private GetUserDetailsUseCase.Callback callback;

    public GetUserDetailsUseCaseImpl() {
        DaggerSupport.inject(this);
    }

    @Override
    public void execute(int userId, Callback callback) {
        if (userId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.userId = userId;
        this.callback = callback;
        this.userRepository.getUserById(this.userId, this.repositoryCallback);
    }

    private final UserRepository.UserDetailsCallback repositoryCallback =
            new UserRepository.UserDetailsCallback() {
                @Override
                public void onUserLoaded(User user) {
                    notifyGetUserDetailsSuccessfully(user);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetUserDetailsSuccessfully(final User user) {
        callback.onUserDataLoaded(user);
    }

    private void notifyError(final ErrorBundle errorBundle) {
        callback.onError(errorBundle);
    }
}
