/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.repository.datasource;

import android.content.Context;

import com.example.data.cache.UserCache;
import com.example.data.cache.UserCacheImpl;
import com.example.data.network.RestApi;
import com.example.data.network.RestApiImpl;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
@EBean
public class UserDataStoreFactory {

    @RootContext
    protected Context context;

    @Bean(UserCacheImpl.class)
    protected UserCache userCache;

    @Bean(RestApiImpl.class)
    protected RestApi restApi;

    /**
     * Create {@link UserDataStore} from a user id.
     */
    public UserDataStore create(int userId) {
        UserDataStore userDataStore;

        if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
            userDataStore = new DiskUserDataStore(this.userCache);
        } else {
            userDataStore = createCloudDataStore();
        }

        return userDataStore;
    }

    /**
     * Create {@link UserDataStore} to retrieve data from the Cloud.
     */
    public UserDataStore createCloudDataStore() {
        return new CloudUserDataStore(restApi, this.userCache);
    }
}
