/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.presenter;

import android.content.Context;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.PresentationSpec;
import com.example.presentation.mapper.UserModelDataMapper;
import com.example.presentation.page.userlist.GetUserListUseCaseCallback;
import com.example.presentation.page.userlist.presenter.UserListPresenter;
import com.example.presentation.page.userlist.view.UserListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class UserListPresenterTest extends PresentationSpec {

    @InjectMocks
    private UserListPresenter userListPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private UserListView mockUserListView;
    @Mock
    private GetUserListUseCase mockGetUserListUseCase;
    @Mock
    private GetUserListUseCaseCallback mockGetUserListUseCaseCallback;
    @Mock
    private UserModelDataMapper mockUserModelDataMapper;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserListPresenterInitialize() {
        doNothing().when(mockGetUserListUseCase).execute(any(GetUserListUseCase.Callback.class));
        given(mockUserListView.getContext()).willReturn(mockContext);

        userListPresenter.initialize(mockUserListView);

        verify(mockUserListView).hideRetry();
        verify(mockUserListView).showLoading();
        verify(mockGetUserListUseCase).execute(any(GetUserListUseCase.Callback.class));
    }
}
