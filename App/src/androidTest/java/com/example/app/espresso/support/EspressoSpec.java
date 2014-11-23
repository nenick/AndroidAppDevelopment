package com.example.app.espresso.support;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.example.app.DummyLauncherActivity;
import com.google.android.apps.common.testing.testrunner.ActivityLifecycleMonitorRegistry;
import com.google.android.apps.common.testing.testrunner.Stage;

import java.util.ArrayList;
import java.util.Collection;

public abstract class EspressoSpec extends ActivityInstrumentationTestCase2<DummyLauncherActivity> {

    public EspressoSpec() {
        super(DummyLauncherActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity(); // Espresso will not launch our activity for us, we must launch it via getActivity().

        // sometimes tests failed on emulator, following approach should avoid it
        // http://stackoverflow.com/questions/22737476/false-positives-junit-framework-assertionfailederror-edittext-is-not-found
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Activity activity = getActivity();
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    @Override
    protected void tearDown() {
        for (Activity activity : collectAllActivityInstances()) {
            activity.finish();
        }
    }

    public Activity getCurrentActivity() {
        getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    java.util.Collection<Activity> activites = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                    activity[0] = (Activity) activites.toArray()[0];
                }});
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        return activity[0];
    }

    private Collection<Activity> collectAllActivityInstances() {
        final Collection<Activity> activities = new ArrayList<Activity>();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activities.addAll(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));
                activities.addAll(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.PAUSED));
                activities.addAll(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.STOPPED));
            }
        });
        return activities;
    }
}
