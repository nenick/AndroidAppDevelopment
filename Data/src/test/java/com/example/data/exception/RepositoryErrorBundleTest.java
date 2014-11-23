/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.exception;

import com.example.data.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RepositoryErrorBundleTest extends ApplicationTestCase {

    @InjectMocks
    private RepositoryErrorBundle repositoryErrorBundle;

    @Mock
    private Exception mockException;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetErrorMessageInteraction() {
        repositoryErrorBundle.getErrorMessage();

        verify(mockException).getMessage();
    }
}
