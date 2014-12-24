package com.example.presentation.userlist;


import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.PresentationSpec;
import com.example.presentation.R;
import com.example.presentation.page.userdetails.view.UserDetailsActivity;
import com.example.presentation.page.userlist.presenter.GetUserListUseCaseCallback_;
import com.example.shared.exception.ErrorBundle;
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
    ArgumentCaptor<GetUserListUseCaseCallback_> callbackArgumentCaptor = ArgumentCaptor.forClass(GetUserListUseCaseCallback_.class);

    @Before
    public void setUp() {
        Robolectric.getBackgroundScheduler().pause();
    }

    @Test
    public void shouldInitialGetUserListAtBackgroundWithProgressBar() {
        givenStartedViewWithPendingTasks();
        thenGetUserListStartedAtBackground();
        thenProgressBarIsVisible();
    }

    @Test
    public void shouldRefreshUserListAtBackgroundWithProgressBar_WhenButtonClick() {
        givenStartedView();
        reset(domainModuleMock.getUserListUseCase);
        whenClickRefreshButton();
        thenGetUserListStartedAtBackground();
        thenProgressBarIsVisible();
    }

    @Test
    public void shouldStopProgressBarAtInitial_WhenGetUserListSuccess() {
        givenStartedViewWithPendingTasks();
        thenProgressBarIsVisible();
        whenGetUserListsFinishedSuccessful();
        thenProgressBarIsGone();
    }

    @Test
    public void shouldStopProgressBarAtRefresh_WhenGetUserListSuccess() {
        givenStartedView();
        whenClickRefreshButton();
        thenProgressBarIsVisible();
        whenGetUserListsFinishedSuccessful();
        thenProgressBarIsGone();
    }

    @Test
    public void shouldStopProgressBarAtInitial_WhenGetUserListFailed() {
        givenStartedViewWithPendingTasks();
        whenGetUserListsFinishedFailed();
        thenProgressBarIsGone();
    }

    @Test
    public void shouldStopProgressBarAtRefresh_WhenGetUserListFailed() {
        givenStartedView();
        whenClickRefreshButton();
        whenGetUserListsFinishedFailed();
        thenProgressBarIsGone();
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
    }


    private void givenStartedViewWithUsers() {
        givenStartedView();
        givenTwoUsersAsResult();
        whenGetUserListReturn(userListResult);
        initialiseListItems();
    }

    private void whenGetUserListsFinishedSuccessful() {
        Robolectric.runBackgroundTasks();
        givenStartedViewWithUsers();
        whenGetUserListReturn(userListResult);
    }

    private void whenGetUserListsFinishedFailed() {
        Robolectric.runBackgroundTasks();
        givenStartedViewWithUsers();
        verify(domainModuleMock.getUserListUseCase).execute(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onError(new ErrorBundle() {
            @Override
            public Exception getException() {
                return new NullPointerException();
            }

            @Override
            public Error getError() {
                return Error.UnexpectedException;
            }

            @Override
            public String getErrorMessage() {
                return "Dummy general error";
            }
        });
    }

    private void whenSelectUserOnListPosition(int position) {
        ListView userList = (ListView) userListActivity.findViewById(R.id.lv_users);
        assertThat(userList.performItemClick(userList, position, userList.getAdapter().getItemId(position))).isTrue();
    }

    private void whenGetUserListReturn(ArrayList<User> users) {
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

    private void thenProgressBarIsVisible() {
        assertThat(Robolectric.shadowOf(userListActivity.getWindow()).getIndeterminateProgressBar().isIndeterminate()).isTrue();
        ANDROID.assertThat(userListActivity.findViewById(R.id.rl_progress)).isVisible();
    }

    private void thenProgressBarIsGone() {
        ANDROID.assertThat(Robolectric.shadowOf(userListActivity.getWindow()).getIndeterminateProgressBar().getRootView()).isVisible();
        ANDROID.assertThat(userListActivity.findViewById(R.id.rl_progress)).isGone();
    }

    private void initialiseListItems() {
        ListView userList = (ListView) userListActivity.findViewById(R.id.lv_users);
        Robolectric.shadowOf(userList).populateItems();
    }
}
