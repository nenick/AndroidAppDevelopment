package com.example.app.robolectric.spec;

import android.content.Intent;
import android.widget.Button;

import com.example.app.AppRobolectricTestRunner;
import com.example.presentation.view.activity.MainActivity;
import com.example.presentation.view.activity.UserDetailsActivity;
import com.example.presentation.view.activity.UserListActivity;
import com.example.robolectric.support.RobolectricTasks;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

@RunWith(AppRobolectricTestRunner.class)
public class RobolectricIntegrationTest {

    @After
    public void tearDown() {
        RobolectricTasks.runAllTasks();
    }

    @Test
    public void shouldStartApp() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().visible().get();
        TestCase.assertTrue(mainActivity != null);

        Button button = (Button) mainActivity.findViewById(com.example.presentation.R.id.btn_LoadData);
        button.performClick();

        Intent nextStartedActivity = Robolectric.shadowOf(mainActivity).getNextStartedActivity();
        TestCase.assertTrue(nextStartedActivity != null);
        TestCase.assertEquals(nextStartedActivity.getComponent().getClassName(), UserListActivity.class.getName());
    }

    @Test
    public void shouldStartList() {
        UserListActivity mainActivity = Robolectric.buildActivity(UserListActivity.class).create().start().resume().visible().get();
        TestCase.assertTrue(mainActivity != null);
    }

    @Test
    public void shouldStartDetails() {
        Intent callingIntent = UserDetailsActivity.getCallingIntent(Robolectric.application, 5);
        UserDetailsActivity mainActivity = Robolectric.buildActivity(UserDetailsActivity.class).withIntent(callingIntent).create().start().resume().visible().get();
        TestCase.assertTrue(mainActivity != null);
    }
}
