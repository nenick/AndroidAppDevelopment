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

import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetUserListUseCaseTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private GetUserListUseCaseImpl getUserListUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserListUseCaseExecution() {

        GetUserListUseCase.Callback mockGetUserListCallback = mock(GetUserListUseCase.Callback.class);

        getUserListUseCase.execute(mockGetUserListCallback);

//        verifyZeroInteractions(mockUserRepository);
    }

    @Test
    public void testGetUserListUseCaseInteractorRun() {
        GetUserListUseCase.Callback mockGetUserListCallback = mock(GetUserListUseCase.Callback.class);

        doNothing().when(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));

        getUserListUseCase.execute(mockGetUserListCallback);

        verify(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));
        verifyNoMoreInteractions(mockUserRepository);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUserListUseCaseCallbackSuccessful() {
        final GetUserListUseCase.Callback mockGetUserListCallback =
                mock(GetUserListUseCase.Callback.class);
        final Collection<User> mockResponseUserList = (Collection<User>) mock(Collection.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserRepository.UserListCallback) invocation.getArguments()[0]).onUserListLoaded(
                        mockResponseUserList);
                return null;
            }
        }).when(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));

        getUserListUseCase.execute(mockGetUserListCallback);

//        verifyNoMoreInteractions(mockGetUserListCallback);
        verifyZeroInteractions(mockResponseUserList);
    }

    @Test
    public void testUserListUseCaseCallbackError() {
        final GetUserListUseCase.Callback mockGetUserListUseCaseCallback =
                mock(GetUserListUseCase.Callback.class);
        final ErrorBundle mockErrorBundle = mock(ErrorBundle.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserRepository.UserListCallback) invocation.getArguments()[0]).onError(mockErrorBundle);
                return null;
            }
        }).when(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));

        getUserListUseCase.execute(mockGetUserListUseCaseCallback);

//        verifyNoMoreInteractions(mockGetUserListUseCaseCallback);
        verifyZeroInteractions(mockErrorBundle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteUserListUseCaseNullParameter() {
        getUserListUseCase.execute(null);
    }
}
