/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.repository;

import com.example.data.entity.UserEntity;
import com.example.data.entity.mapper.UserEntityDataMapper;
import com.example.data.exception.RepositoryErrorBundle;
import com.example.data.repository.datasource.UserDataStore;
import com.example.data.repository.datasource.UserDataStoreFactory;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Collection;

/**
 * {@link UserRepository} for retrieving user data.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserDataRepository implements UserRepository {

    @Bean
    protected UserDataStoreFactory userDataStoreFactory;
    @Bean
    protected UserEntityDataMapper userEntityDataMapper;

    /**
     * {@inheritDoc}
     *
     * @param userListCallback A {@link UserListCallback} used for notifying clients.
     */
    @Override
    public void getUserList(final UserListCallback userListCallback) {
        //we always get all users from the cloud
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        userDataStore.getUsersEntityList(new UserDataStore.UserListCallback() {
            @Override
            public void onUserListLoaded(Collection<UserEntity> usersCollection) {
                Collection<User> users =
                        UserDataRepository.this.userEntityDataMapper.transform(usersCollection);
                userListCallback.onUserListLoaded(users);
            }

            @Override
            public void onError(Exception exception) {
                userListCallback.onError(new RepositoryErrorBundle(ErrorBundle.Error.UnexpectedException, exception));
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param userId       The user id used to retrieve user data.
     * @param userCallback A {@link com.example.datamodel.repository.UserRepository.UserDetailsCallback}
     *                     used for notifying clients.
     */
    @Override
    public void getUserById(final int userId, final UserDetailsCallback userCallback) {
        UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
        userDataStore.getUserEntityDetails(userId, new UserDataStore.UserDetailsCallback() {
            @Override
            public void onUserEntityLoaded(UserEntity userEntity) {
                User user = UserDataRepository.this.userEntityDataMapper.transform(userEntity);
                if (user != null) {
                    userCallback.onUserLoaded(user);
                } else {
                    userCallback.onError(new RepositoryErrorBundle(ErrorBundle.Error.UserNotFound));
                }
            }

            @Override
            public void onError(Exception exception) {
                userCallback.onError(new RepositoryErrorBundle(ErrorBundle.Error.UnexpectedException, exception));
            }
        });
    }
}
