package com.example.robolectric.support;


import org.androidannotations.api.BackgroundExecutor;
import org.fest.util.Strings;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure synchronous execution of android annotations background cancelableTasks.
 * <p/>
 * Robolectric is designed to execute all background cancelableTasks synced to avoid flaky tests. Current
 * it miss support for android annotations so this class will do the job.
 */
@Implements(BackgroundExecutor.class)
public class ShadowBackgroundExecutor {

    static Map<String, Runnable> cancelableTasks = new HashMap<>();

    @Implementation
    public static synchronized void execute(final BackgroundExecutor.Task task) {
        String taskId = extractTaskId(task);

        if (Strings.isNullOrEmpty(taskId)) {
            Robolectric.getBackgroundScheduler().post(task);
        } else {
            Runnable taskWrapper = wrapAsCancelableTask(task, taskId);
            Robolectric.getBackgroundScheduler().post(taskWrapper);
        }
    }

    /**
     * Stop the start of queued background cancelableTasks. To us it you must stop automatic
     * background task execution with Robolectric.getBackgroundScheduler().pause();
     * <p/>
     * The interrupt feature is not supported because there is no async with robolectric tests.
     */
    public static synchronized void cancelAll(String id, boolean mayInterruptIfRunning) {
        Runnable runnable = cancelableTasks.remove(id);
        Robolectric.getBackgroundScheduler().remove(runnable);
    }

    private static Runnable wrapAsCancelableTask(final BackgroundExecutor.Task task, String taskId) {
        final String fTaskId = taskId;
        Runnable taskWrapper = new Runnable() {
            @Override
            public void run() {
                task.run();
                cancelableTasks.remove(fTaskId);
            }
        };
        cancelableTasks.put(fTaskId, taskWrapper);
        return taskWrapper;
    }

    private static String extractTaskId(BackgroundExecutor.Task task) {
        try {
            Field privateStringField = BackgroundExecutor.Task.class.getDeclaredField("id");
            privateStringField.setAccessible(true);
            return (String) privateStringField.get(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}