/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.domain.interactor;

import com.example.shared.model.User;
import com.example.shared.exception.ErrorBundle;

/**
 * This interface represents a execution unit for a use case to get data for an specific user.
 * By convention this use case implementation will return the result using a Callback.
 */
public interface GetUserDetailsUseCase {
  /**
   * Callback used to be notified when either a user has been loaded or an error happened.
   */
  interface Callback {
    void onUserDataLoaded(User user);
    void onError(ErrorBundle errorBundle);
  }

  /**
   * Executes this user case.
   *
   * @param userId The user id to retrieve.
   * @param callback A {@link GetUserDetailsUseCase.Callback} used for notify the client.
   */
  public void execute(int userId, Callback callback);
}
