/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.page.userdetails.presenter;

import android.content.Context;

import com.example.domain.interactor.GetUserDetailsUseCase;
import com.example.presentation.PresentationSpec;
import com.example.presentation.page.userdetails.view.UserDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class UserDetailsPresenterTest extends PresentationSpec {

    private static final int FAKE_USER_ID = 123;

    @InjectMocks
    private UserDetailsPresenter userDetailsPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private UserDetailsView mockUserDetailsView;
    @Mock
    private GetUserDetailsUseCase mockGetUserDetailsUseCase;
    @Mock
    private GetUserDetailsUseCaseCallback mockGetUserDetailsUseCaseCallback;
    @Mock
    private UserModelDataMapper mockUserModelDataMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserDetailsPresenterInitialize() {
        doNothing().when(mockGetUserDetailsUseCase)
                .execute(anyInt(), any(GetUserDetailsUseCase.Callback.class));
        given(mockUserDetailsView.getContext()).willReturn(mockContext);

        userDetailsPresenter.initialize(mockUserDetailsView, FAKE_USER_ID);

        verify(mockUserDetailsView).hideRetry();
        verify(mockUserDetailsView).showLoading();
        verify(mockGetUserDetailsUseCase).execute(anyInt(), any(GetUserDetailsUseCase.Callback.class));
    }
}
