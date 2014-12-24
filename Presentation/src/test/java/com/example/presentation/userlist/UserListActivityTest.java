package com.example.presentation.userlist;


import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.PresentationSpec;
import com.example.presentation.R;
import com.example.presentation.page.userdetails.view.UserDetailsActivity;
import com.example.presentation.page.userlist.presenter.GetUserListUseCaseCallback_;
import com.example.shared.model.User;

import org.fest.assertions.api.ANDROID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class UserListActivityTest extends PresentationSpec {

    UserListActivity userListActivity;
    ArrayList<User> userListResult;

    @Before
    public void setUp() {
        Robolectric.getBackgroundScheduler().pause();
    }

    @Test
    public void shouldInitialGetUserListAtBackground() {
        givenStartedViewWithPendingTasks();
        thenGetUserListStartedAtBackground();
    }

    @Test
    public void shouldRefreshUserListAtBackground_WhenButtonClick() {
        givenStartedView();
        whenClickRefreshButton();
        thenGetUserListStartedAtBackground();
    }

    @Test
    public void shouldShowUsers_WhenGetUserListReturnsSome() {
        givenStartedView();
        givenTwoUsersAsResult();
        whenGetUserListReturn(userListResult);
        thenUserListHasCount(userListResult.size());
    }

    @Test
    public void shouldStartUserDetails_WhenUserIsSelected() {
        givenStartedViewWithUsers();
        whenSelectUserOnListPosition(0);
        thenUserDetailsIsStarted(userListResult.get(0).getUserId());
        whenSelectUserOnListPosition(1);
        thenUserDetailsIsStarted(userListResult.get(1).getUserId());
    }

    @Test
    public void shouldShowEmptyList_WhenGetUserListReturnEmpty() {
        givenStartedView();
        givenEmptyUserListAsResult();
        whenGetUserListReturn(userListResult);
        thenUserListHasCount(userListResult.size());
    }

    private void givenEmptyUserListAsResult() {
        userListResult = new ArrayList<>();
    }

    private void givenTwoUsersAsResult() {
        userListResult = new ArrayList<>();
        User user1 = new User(1);
        user1.setFullName("User1");
        userListResult.add(user1);
        User user2 = new User(2);
        user1.setFullName("User2");
        userListResult.add(user2);
    }

    private void givenStartedViewWithPendingTasks() {
        userListActivity = Robolectric.buildActivity(UserListActivity_.class).create().start().visible().resume().get();
    }

    private void givenStartedView() {
        givenStartedViewWithPendingTasks();
        Robolectric.runBackgroundTasks();
        reset(domainModuleMock.getUserListUseCase);
    }


    private void givenStartedViewWithUsers() {
        givenStartedView();
        givenTwoUsersAsResult();
        whenGetUserListReturn(userListResult);
        initialiseListItems();
    }

    private void whenSelectUserOnListPosition(int position) {
        ListView userList = (ListView) userListActivity.findViewById(R.id.lv_users);
        assertThat(userList.performItemClick(userList, position, userList.getAdapter().getItemId(position))).isTrue();
    }

    private void whenGetUserListReturn(ArrayList<User> users) {
        ArgumentCaptor<GetUserListUseCaseCallback_> callbackArgumentCaptor = ArgumentCaptor.forClass(GetUserListUseCaseCallback_.class);
        verify(domainModuleMock.getUserListUseCase).execute(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onUserListLoaded(users);
    }

    private void whenClickRefreshButton() {
        Button button = (Button) userListActivity.findViewById(R.id.bt_retry);
        assertThat(button.performClick()).isTrue();
    }

    private void thenGetUserListStartedAtBackground() {
        verify(domainModuleMock.getUserListUseCase, never()).execute(any(GetUserListUseCase.Callback.class));
        assertThat(Robolectric.getBackgroundScheduler().enqueuedTaskCount()).isEqualTo(1);
        Robolectric.getBackgroundScheduler().runOneTask();
        verify(domainModuleMock.getUserListUseCase).execute(any(GetUserListUseCase.Callback.class));
    }

    private void thenUserListHasCount(int count) {
        ListView userList = (ListView) userListActivity.findViewById(R.id.lv_users);
        ANDROID.assertThat(userList).hasCount(count);
        initialiseListItems();
        ANDROID.assertThat(userList).hasChildCount(count);
    }

    private void thenUserDetailsIsStarted(int userId) {
        Intent nextStartedActivity = Robolectric.shadowOf(userListActivity).getNextStartedActivity();
        ANDROID.assertThat(nextStartedActivity).hasComponent(userListActivity, UserDetailsActivity.class);
        ANDROID.assertThat(nextStartedActivity).hasExtra(UserDetailsActivity.INTENT_EXTRA_PARAM_USER_ID);
        assertThat(nextStartedActivity.getIntExtra(UserDetailsActivity.INTENT_EXTRA_PARAM_USER_ID, -1)).isEqualTo(userId);
    }

    private void initialiseListItems() {
        ListView userList = (ListView) userListActivity.findViewById(R.id.lv_users);
        Robolectric.shadowOf(userList).populateItems();
    }
}
