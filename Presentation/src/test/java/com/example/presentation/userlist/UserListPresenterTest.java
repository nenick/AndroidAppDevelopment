package com.example.presentation.userlist;

import com.example.domain.interactor.GetUserListUseCase;
import com.example.presentation.PresentationSpec;
import com.example.presentation.TestErrorBundle;
import com.example.shared.exception.ErrorBundle;
import com.example.shared.model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import java.util.ArrayList;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserListPresenterTest extends PresentationSpec {

    @InjectMocks
    UserListPresenter userListPresenter;

    @Mock
    UserListView mockUserListView;

    GetUserListUseCase mockGetUserListUseCase;

    UserModelDataMapper userModelDataMapper = new UserModelDataMapper();

    Collection<User> emptyUserCollection = new ArrayList<>();

    UserModel user;

    ArgumentCaptor<GetUserListUseCase.Callback> callbackArgumentCaptor = ArgumentCaptor.forClass(GetUserListUseCase.Callback.class);

    private TestErrorBundle errorBundle = new TestErrorBundle();

    @Before
    public void setUp() throws Exception {
        userListPresenter = UserListPresenter_.getInstance_(context);
        MockitoAnnotations.initMocks(this);
        mockGetUserListUseCase = domainModuleMock.getUserListUseCase;
        given(mockUserListView.getContext()).willReturn(context);
        user = new UserModel(678);
        user.setFullName("duck duck");
    }

    @Test
    public void testStartGetUserList_shouldHideRetryOption() {
        givenPresenterStartsInitialisation();
        verify(mockUserListView).hideRetry();
    }

    @Test
    public void testStartGetUserList_shouldShowLoadingAnimation() {
        givenPresenterStartsInitialisation();
        verify(mockUserListView).showLoading();
    }

    @Test
    public void testGetUserListSuccessful_shouldHideLoadingAnimation() {
        givenPresenterStartsInitialisation();
        whenGetUserListFinishSuccessful(emptyUserCollection);
        verify(mockUserListView).hideLoading();
    }

    @Test
    public void testGetUserListSuccessful_shouldShowUserList() {
        givenPresenterStartsInitialisation();
        whenGetUserListFinishSuccessful(emptyUserCollection);
        verify(mockUserListView).renderUserList(userModelDataMapper.transform(emptyUserCollection));
    }

    @Test
    public void testOnViewCreated_shouldStartGetUserListOnBackground() {
        Robolectric.getBackgroundScheduler().pause();
        givenPresenterStartsInitialisation();
        thenGetUserListStartedAtBackground();
    }

    @Test
    public void testOnViewDestroyed_shouldStopGetUserList() {
        Robolectric.getBackgroundScheduler().pause();
        givenPresenterStartsInitialisation();
        userListPresenter.onViewDestroyed();
        thenGetUserListWasStopped();
    }

    @Test
    public void testGetUserListFailed_shouldHideLoadingAnimation() {
        givenPresenterStartsInitialisation();
        whenGetUserListFinishWithError(ErrorBundle.Error.UnexpectedException);
        verify(mockUserListView).hideLoading();
    }

    @Test
    public void testGetUserListFailed_shouldShowRetryOption() {
        givenPresenterStartsInitialisation();
        whenGetUserListFinishWithError(ErrorBundle.Error.UnexpectedException);
        verify(mockUserListView).showRetry();
    }

    @Test
    public void testGetUserListFailed_shouldShowErrorMessage_unexpected() {
        givenPresenterStartsInitialisation();
        whenGetUserListFinishWithError(ErrorBundle.Error.UnexpectedException);
        verify(mockUserListView).showError("There was an application error");
    }

    @Test
    public void testGetUserListFailed_shouldShowErrorMessage_network() {
        givenPresenterStartsInitialisation();
        whenGetUserListFinishWithError(ErrorBundle.Error.NetworkConnection);
        verify(mockUserListView).showError("There is no internet connection");
    }

    @Test
    public void testOnUserSelected_shouldShowUserDetails() {
        userListPresenter.onUserClicked(user);
        verify(mockUserListView).viewUser(user);
    }

    private void givenPresenterStartsInitialisation() {
        userListPresenter.initialize(mockUserListView);
    }

    private void whenGetUserListFinishSuccessful(Collection<User> userCollection) {
        verify(mockGetUserListUseCase).execute(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().success(userCollection);
    }

    private void whenGetUserListFinishWithError(ErrorBundle.Error givenError) {
        verify(mockGetUserListUseCase).execute(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().failed(errorBundle.withError(givenError));
    }

    private void thenGetUserListStartedAtBackground() {
        verify(mockGetUserListUseCase, never()).execute(any(GetUserListUseCase.Callback.class));
        assertThat(Robolectric.getBackgroundScheduler().enqueuedTaskCount()).isEqualTo(1);
        Robolectric.getBackgroundScheduler().runOneTask();
        verify(mockGetUserListUseCase).execute(any(GetUserListUseCase.Callback.class));
    }

    private void thenGetUserListWasStopped() {
        verify(mockGetUserListUseCase, never()).execute(any(GetUserListUseCase.Callback.class));
        assertThat(Robolectric.getBackgroundScheduler().enqueuedTaskCount()).isEqualTo(0);
    }
}
