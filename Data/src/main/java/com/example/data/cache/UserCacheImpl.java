/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.cache;

import android.content.Context;

import com.example.data.cache.serializer.JsonSerializer;
import com.example.data.entity.UserEntity;
import com.example.data.exception.UserNotFoundException;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.File;

/**
 * {@link UserCache} implementation.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserCacheImpl implements UserCache {

    private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "user_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    @RootContext
    protected Context context;

    protected File cacheDir;
    @Bean
    protected JsonSerializer serializer;
    @Bean
    protected FileManager fileManager;

    @AfterInject
    void initCacheDir() {
        cacheDir = context.getCacheDir();
    }

    /**
     * {@inheritDoc}
     *
     * @param userId   The user id to retrieve data.
     * @param callback The {@link UserCacheCallback} to notify the client.
     */
    @Override
    public synchronized void get(int userId, UserCacheCallback callback) {
        File userEntitiyFile = this.buildFile(userId);
        String fileContent = this.fileManager.readFileContent(userEntitiyFile);
        UserEntity userEntity = this.serializer.deserialize(fileContent);

        if (userEntity != null) {
            callback.onUserEntityLoaded(userEntity);
        } else {
            callback.onError(new UserNotFoundException());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userEntity Element to insert in the cache.
     */
    @Override
    public synchronized void put(UserEntity userEntity) {
        if (userEntity != null) {
            File userEntitiyFile = this.buildFile(userEntity.getUserId());
            if (!isCached(userEntity.getUserId())) {
                String jsonString = this.serializer.serialize(userEntity);
                new CacheWriter(this.fileManager, userEntitiyFile,
                        jsonString).run();
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    @Override
    public boolean isCached(int userId) {
        File userEntitiyFile = this.buildFile(userId);
        return this.fileManager.exists(userEntitiyFile);
    }

    /**
     * {@inheritDoc}
     *
     * @return true, the cache is expired, otherwise false.
     */
    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void evictAll() {
        new CacheEvictor(this.fileManager, this.cacheDir).run();
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param userId The id user to build the file.
     * @return A valid file.
     */
    private File buildFile(int userId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(userId);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
