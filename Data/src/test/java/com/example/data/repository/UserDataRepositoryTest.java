/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.repository;

import com.example.data.ApplicationTestCase;
import com.example.data.cache.UserCache;
import com.example.data.entity.UserEntity;
import com.example.data.entity.mapper.UserEntityDataMapper;
import com.example.data.exception.RepositoryErrorBundle;
import com.example.data.repository.datasource.CloudUserDataStore;
import com.example.data.repository.datasource.DiskUserDataStore;
import com.example.data.repository.datasource.UserDataStore;
import com.example.shared.model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class UserDataRepositoryTest extends ApplicationTestCase {

    private static final int FAKE_USER_ID = 123;

    @InjectMocks
    private UserDataRepository userDataRepository;

    @Mock
    UserCache mockUserCache;
    @Mock
    private UserEntityDataMapper mockUserEntityDataMapper;
    @Mock
    CloudUserDataStore cloudUserDataStore;
    @Mock
    DiskUserDataStore diskUserDataStore;

    @Mock
    private UserEntity mockUserEntity;
    @Mock
    private User mockUser;
    @Mock
    private UserRepository.UserDetailsCallback mockUserDetailsRepositoryCallback;
    @Mock
    private UserRepository.UserListCallback mockUserListRepositoryCallback;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserDetailsSuccess() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserDataStore.UserDetailsCallback) invocation.getArguments()[1]).onUserEntityLoaded(
                        mockUserEntity);
                return null;
            }
        }).when(cloudUserDataStore).getUserEntityDetails(anyInt(),
                any(UserDataStore.UserDetailsCallback.class));
        given(mockUserEntityDataMapper.transform(mockUserEntity)).willReturn(mockUser);

        userDataRepository.getUserById(FAKE_USER_ID, mockUserDetailsRepositoryCallback);

        verify(mockUserEntityDataMapper).transform(mockUserEntity);
        verify(mockUserDetailsRepositoryCallback).onUserLoaded(mockUser);
        verify(mockUserCache).put(mockUserEntity);
    }

    @Test
    public void testGetUserDetailsNullResult() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserDataStore.UserDetailsCallback) invocation.getArguments()[1]).onUserEntityLoaded(
                        mockUserEntity);
                return null;
            }
        }).when(cloudUserDataStore).getUserEntityDetails(anyInt(),
                any(UserDataStore.UserDetailsCallback.class));
        given(mockUserEntityDataMapper.transform(mockUserEntity)).willReturn(null);

        doNothing().when(mockUserDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));

        userDataRepository.getUserById(FAKE_USER_ID, mockUserDetailsRepositoryCallback);

        verify(mockUserEntityDataMapper).transform(mockUserEntity);
        verify(mockUserDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));
    }

    @Test
    public void testGetUserByIdError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((UserDataStore.UserDetailsCallback) invocation.getArguments()[1]).onError(
                        any(Exception.class));
                return null;
            }
        }).when(cloudUserDataStore).getUserEntityDetails(anyInt(),
                any(UserDataStore.UserDetailsCallback.class));

        userDataRepository.getUserById(FAKE_USER_ID, mockUserDetailsRepositoryCallback);

        verify(mockUserDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));
        verifyZeroInteractions(mockUserEntityDataMapper);
    }
}
