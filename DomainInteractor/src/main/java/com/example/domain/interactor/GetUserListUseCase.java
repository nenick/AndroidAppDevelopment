/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.domain.interactor;

import com.example.shared.model.User;
import com.example.shared.exception.ErrorBundle;
import java.util.Collection;

/**
 * This interface represents a execution unit for a use case to get a collection of Users.
 * By convention this use case implementation will return the result using a Callback.
 */
public interface GetUserListUseCase {
  /**
   * Callback used to be notified when either a users collection has been loaded or an error
   * happened.
   */
  interface Callback {
    void success(Collection<User> usersCollection);
    void failed(ErrorBundle errorBundle);
  }

  /**
   * Executes this user case.
   *
   * @param callback A {@link GetUserListUseCase.Callback} used to notify the client.
   */
  void execute(Callback callback);
}
