/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.repository.datasource;

import com.example.data.entity.UserEntity;
import com.example.data.network.RestApi;
import com.example.data.network.RestApiImpl;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Collection;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
@EBean
public class CloudUserDataStore implements UserDataStore {

    @Bean(RestApiImpl.class)
    protected RestApi restApi;

    /**
     * {@inheritDoc}
     *
     * @param userListCallback A {@link UserListCallback} used for notifying clients.
     */
    @Override
    public void getUsersEntityList(final UserListCallback userListCallback) {
        this.restApi.getUserList(new RestApi.UserListCallback() {
            @Override
            public void onUserListLoaded(Collection<UserEntity> usersCollection) {
                userListCallback.onUserListLoaded(usersCollection);
            }

            @Override
            public void onError(Exception exception) {
                userListCallback.onError(exception);
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param id                  The user id used to retrieve user data.
     * @param userDetailsCallback A {@link UserDetailsCallback} used for notifying clients.
     */
    @Override
    public void getUserEntityDetails(int id,
                                     final UserDetailsCallback userDetailsCallback) {
        this.restApi.getUserById(id, new RestApi.UserDetailsCallback() {
            @Override
            public void onUserEntityLoaded(UserEntity userEntity) {
                userDetailsCallback.onUserEntityLoaded(userEntity);
            }

            @Override
            public void onError(Exception exception) {
                userDetailsCallback.onError(exception);
            }
        });
    }


}
