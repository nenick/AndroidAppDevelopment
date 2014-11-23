/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.cache;

import com.example.data.ApplicationTestCase;
import com.example.data.cache.serializer.JsonSerializer;
import com.example.data.entity.UserEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import java.io.File;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class UserCacheImplTest extends ApplicationTestCase {

    private static final int FAKE_USER_ID = 123;

    @InjectMocks
    private UserCacheImpl userCache;

    @Mock
    private JsonSerializer mockJsonSerializer;
    @Mock
    private FileManager mockFileManager;
    @Mock
    File cacheDir;
    @Mock
    private UserCache.UserCacheCallback mockUserCacheCallback;
    @Mock
    private UserEntity mockUserEntity;

    @Before
    public void setUp() {
        cacheDir = Robolectric.application.getCacheDir();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFromCacheHappyCase() {
        given(mockJsonSerializer.deserialize(anyString())).willReturn(mockUserEntity);

        userCache.get(FAKE_USER_ID, mockUserCacheCallback);

        verify(mockFileManager).readFileContent(any(File.class));
        verify(mockJsonSerializer).deserialize(anyString());
        verify(mockUserCacheCallback).onUserEntityLoaded(mockUserEntity);
    }
}
