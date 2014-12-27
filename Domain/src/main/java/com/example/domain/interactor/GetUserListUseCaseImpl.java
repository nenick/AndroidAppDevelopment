/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.domain.interactor;

import com.example.data.repository.UserRepository;
import com.example.shared.dagger.DaggerSupport;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import java.util.Collection;

import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetUserListUseCase} that represents a use case for
 * retrieving a collection of all {@link User}.
 */
public class GetUserListUseCaseImpl implements GetUserListUseCase {

    @Inject
    protected UserRepository userRepository;
    private Callback callback;

    public GetUserListUseCaseImpl() {
        DaggerSupport.inject(this);
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.userRepository.getUserList(this.repositoryCallback);
    }

    private final UserRepository.UserListCallback repositoryCallback =
            new UserRepository.UserListCallback() {
                @Override
                public void onUserListLoaded(Collection<User> usersCollection) {
                    notifyGetUserListSuccessfully(usersCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetUserListSuccessfully(final Collection<User> usersCollection) {
        callback.success(usersCollection);
    }

    private void notifyError(final ErrorBundle errorBundle) {
        callback.failed(errorBundle);
    }
}
