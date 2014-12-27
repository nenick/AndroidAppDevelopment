/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.app.espresso.spec;

import android.app.Fragment;

import com.example.app.espresso.support.EspressoSpec;
import com.example.presentation.R;
import com.example.presentation.userlist.UserListActivity_;
import com.google.android.apps.common.testing.ui.espresso.Espresso;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserListActivityTest extends EspressoSpec {


    @Override
    public void setUp() throws Exception {
        super.setUp();
        new UserListActivity_.IntentBuilder_(getActivity()).start();
        Espresso.closeSoftKeyboard();
    }

    public void testContainsAUserListFragment() {
        Fragment userListFragment =
                getCurrentActivity().getFragmentManager().findFragmentById(R.id.fragmentUserList);
        assertThat(userListFragment, is(notNullValue()));
    }

    public void testContainsProperTitle() {
        String actualTitle = getCurrentActivity().getTitle().toString().trim();

        assertThat(actualTitle, is("Users List"));
    }
}
