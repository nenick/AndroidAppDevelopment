package com.example.robolectric.support;


import org.androidannotations.api.BackgroundExecutor;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowAsyncTask;

/**
 * Ensure synchronous execution of android annotations background tasks.
 *
 * Robolectric is designed to execute all background tasks synced to avoid flaky tests. Current
 * it miss support for android annotations so this class will do the job.
 */
@Implements(BackgroundExecutor.class)
public class ShadowBackgroundExecutor {

    @Implementation
    public static synchronized void execute(final BackgroundExecutor.Task task) {
        new ShadowAsyncTask<Void, Void, Void>();

        Robolectric.getBackgroundScheduler().post(task);
    }
}