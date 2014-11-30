/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.repository;

import com.example.data.cache.UserCache;
import com.example.data.cache.UserCacheImpl;
import com.example.data.entity.UserEntity;
import com.example.data.entity.mapper.UserEntityDataMapper;
import com.example.data.exception.RepositoryErrorBundle;
import com.example.data.repository.datasource.CloudUserDataStore;
import com.example.data.repository.datasource.DiskUserDataStore;
import com.example.data.repository.datasource.UserDataStore;
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
    protected UserEntityDataMapper userEntityDataMapper;

    @Bean(UserCacheImpl.class)
    protected UserCache userCache;
    @Bean
    protected CloudUserDataStore cloudUserDataStore;
    @Bean
    protected DiskUserDataStore diskUserDataStore;

    /**
     * {@inheritDoc}
     *
     * @param userListCallback A {@link UserListCallback} used for notifying clients.
     */
    @Override
    public void getUserList(final UserListCallback userListCallback) {
        //we always get all users from the cloud
        cloudUserDataStore.getUsersEntityList(new UserDataStore.UserListCallback() {
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
     * @param userCallback used for notifying clients.
     */
    @Override
    public void getUserById(final int userId, final UserDetailsCallback userCallback) {
        UserDataStore userDataStore;
        if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
            userDataStore = diskUserDataStore;
        } else {
            userDataStore = cloudUserDataStore;
        }
        userDataStore.getUserEntityDetails(userId, new UserDataStore.UserDetailsCallback() {
            @Override
            public void onUserEntityLoaded(UserEntity userEntity) {
                UserDataRepository.this.putUserEntityInCache(userEntity);
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

    /**
     * Saves a {@link UserEntity} into cache.
     *
     * @param userEntity The {@link UserEntity} to save.
     */
    private void putUserEntityInCache(UserEntity userEntity) {
        if (userEntity != null) {
            this.userCache.put(userEntity);
        }
    }
}
