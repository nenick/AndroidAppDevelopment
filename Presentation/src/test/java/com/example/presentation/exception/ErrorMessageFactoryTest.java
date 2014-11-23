/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.exception;

import com.example.presentation.PresentationSpec;
import com.example.presentation.R;
import com.example.shared.exception.ErrorBundle;

import org.junit.Test;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends PresentationSpec {


    @Test
    public void testNetworkConnectionErrorMessage() {
        String expectedMessage = Robolectric.application.getString(R.string.exception_message_no_connection);
        String actualMessage = ErrorMessageFactory.create(Robolectric.application,
                ErrorBundle.Error.NetworkConnection);

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

    @Test
    public void testUserNotFoundErrorMessage() {
        String expectedMessage = Robolectric.application.getString(R.string.exception_message_user_not_found);
        String actualMessage = ErrorMessageFactory.create(Robolectric.application, ErrorBundle.Error.UserNotFound);

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }
}
