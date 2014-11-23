/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.domain.interactor;

import com.example.data.repository.UserRepository;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetUserDetailsUseCaseTest {

    private static final int FAKE_USER_ID = 123;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private GetUserDetailsUseCaseImpl getUserDetailsUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserDetailsUseCaseExecution() {

        GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
                mock(GetUserDetailsUseCase.Callback.class);

        getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);

     //   verifyZeroInteractions(mockUserRepository);

    }

    @Test
    public void testGetUserDetailsUseCaseInteractorRun() {
        GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
                mock(GetUserDetailsUseCase.Callback.class);


        doNothing().when(mockUserRepository)
                .getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

        getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);


        verify(mockUserRepository).getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

        verifyNoMoreInteractions(mockUserRepository);

    }

    @Test
    public void testUserDetailsUseCaseCallbackSuccessful() {
        final GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
                mock(GetUserDetailsUseCase.Callback.class);
        final User mockResponseUser = mock(User.class);


        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserRepository.UserDetailsCallback) invocation.getArguments()[1]).onUserLoaded(
                        mockResponseUser);
                return null;
            }
        }).when(mockUserRepository)
                .getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

        getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);

//        verifyNoMoreInteractions(mockGetUserDetailsCallback);
        verifyZeroInteractions(mockResponseUser);
    }

    @Test
    public void testUserDetailsUseCaseCallbackError() {
        final GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
                mock(GetUserDetailsUseCase.Callback.class);
        final ErrorBundle mockErrorHandler = mock(ErrorBundle.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserRepository.UserDetailsCallback) invocation.getArguments()[1]).onError(
                        mockErrorHandler);
                return null;
            }
        }).when(mockUserRepository)
                .getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

        getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);

//        verifyNoMoreInteractions(mockGetUserDetailsCallback);
        verifyZeroInteractions(mockErrorHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteUserCaseNullParameter() {
        getUserDetailsUseCase.execute(FAKE_USER_ID, null);
    }
}
