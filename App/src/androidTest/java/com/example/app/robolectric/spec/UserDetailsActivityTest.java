/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.app.robolectric.spec;

import android.app.Fragment;
import android.content.Intent;

import com.example.presentation.R;
import com.example.presentation.view.activity.UserDetailsActivity;
import com.example.app.robolectric.support.EspressoSpec;
import com.google.android.apps.common.testing.ui.espresso.Espresso;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class UserDetailsActivityTest extends EspressoSpec {

    private static final int FAKE_USER_ID = 10;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().startActivity(createTargetIntent());
            }
        });
        Espresso.closeSoftKeyboard();
    }

    public void testContainsUserDetailsFragment() {
        Fragment userDetailsFragment =
                getCurrentActivity().getFragmentManager().findFragmentById(R.id.fl_fragment);
        assertThat(userDetailsFragment, is(notNullValue()));
    }

    public void testContainsProperTitle() {
        //int identifier = getActivity().getResources().getIdentifier("action_bar_title", "id", "android");
        //boolean isVisible = true;
        //if (isVisible) onView(withId(identifier)).check(matches(isDisplayed()));
        //else onView(withId(identifier)).check(doesNotExist());

        String actualTitle = getCurrentActivity().getTitle().toString().trim();

        assertThat(actualTitle, is("User Details"));
    }

    public void testLoadUserHappyCaseViews() {
        onView(withId(R.id.rl_retry)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rl_progress)).check(matches(not(isDisplayed())));

        onView(withId(R.id.tv_fullname)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_email)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()));
    }

    public void testLoadUserHappyCaseData() {
        onView(withId(R.id.tv_fullname)).check(matches(withText("John Sanchez")));
        onView(withId(R.id.tv_email)).check(matches(withText("dmedina@katz.edu")));
        onView(withId(R.id.tv_followers)).check(matches(withText("4523")));
    }

    private Intent createTargetIntent() {
        Intent intentLaunchActivity =
                UserDetailsActivity.getCallingIntent(getInstrumentation().getTargetContext(), FAKE_USER_ID);

        return intentLaunchActivity;
    }
}
