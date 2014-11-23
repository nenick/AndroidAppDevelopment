/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.page.userdetails.presenter;

import com.example.presentation.PresentationSpec;
import com.example.presentation.page.userdetails.view.UserModel;
import com.example.shared.model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class UserModelDataMapperTest extends PresentationSpec {

    private static final int FAKE_USER_ID = 123;
    private static final String FAKE_FULLNAME = "Tony Stark";

    private UserModelDataMapper userModelDataMapper;

    @Before
    public void setUp() throws Exception {
        userModelDataMapper = new UserModelDataMapper();
    }

    @Test
    public void testTransformUser() {
        User user = createFakeUser();
        UserModel userModel = userModelDataMapper.transform(user);

        assertThat(userModel, is(instanceOf(UserModel.class)));
        assertThat(userModel.getUserId(), is(FAKE_USER_ID));
        assertThat(userModel.getFullName(), is(FAKE_FULLNAME));
    }

    @Test
    public void testTransformUserCollection() {
        User mockUserOne = mock(User.class);
        User mockUserTwo = mock(User.class);

        List<User> userList = new ArrayList<User>(5);
        userList.add(mockUserOne);
        userList.add(mockUserTwo);

        Collection<UserModel> userModelList = userModelDataMapper.transform(userList);

        assertThat(userModelList.toArray()[0], is(instanceOf(UserModel.class)));
        assertThat(userModelList.toArray()[1], is(instanceOf(UserModel.class)));
        assertThat(userModelList.size(), is(2));
    }

    private User createFakeUser() {
        User user = new User(FAKE_USER_ID);
        user.setFullName(FAKE_FULLNAME);

        return user;
    }
}
