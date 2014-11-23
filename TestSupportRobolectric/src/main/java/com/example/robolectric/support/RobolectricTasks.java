package com.example.robolectric.support;

import org.robolectric.Robolectric;

public class RobolectricTasks {

    public static void runAllTasks() {
        runAllBackgroundTasks();
        runAllUiThreadTasks();
    }

    public static void runAllUiThreadTasks() {
        while (Robolectric.getUiThreadScheduler().size() > 0) {
            Robolectric.runUiThreadTasksIncludingDelayedTasks();
        }
    }

    public static void runAllBackgroundTasks() {
        while (Robolectric.getBackgroundScheduler().size() > 0) {
            Robolectric.runBackgroundTasks();
        }
    }
}
