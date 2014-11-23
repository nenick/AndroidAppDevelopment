/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.page.userlist.presenter;

import com.example.presentation.page.userlist.view.UserModel;
import com.example.shared.model.User;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Mapper class used to transform {@link com.example.shared.model.User} (in the domain layer) to {@link com.example.presentation.page.userlist.view.UserModel} in the
 * presentation layer.
 */
@EBean
public class UserModelDataMapper {

  /**
   * Transform a {@link com.example.shared.model.User} into an {@link com.example.presentation.page.userlist.view.UserModel}.
   *
   * @param user Object to be transformed.
   * @return {@link com.example.presentation.page.userlist.view.UserModel}.
   */
  public UserModel transform(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    UserModel userModel = new UserModel(user.getUserId());
    userModel.setFullName(user.getFullName());

    return userModel;
  }

  /**
   * Transform a Collection of {@link com.example.shared.model.User} into a Collection of {@link com.example.presentation.page.userlist.view.UserModel}.
   *
   * @param usersCollection Objects to be transformed.
   * @return List of {@link com.example.presentation.page.userlist.view.UserModel}.
   */
  public Collection<UserModel> transform(Collection<User> usersCollection) {
    Collection<UserModel> userModelsCollection;

    if (usersCollection != null && !usersCollection.isEmpty()) {
      userModelsCollection = new ArrayList<UserModel>();
      for (User user : usersCollection) {
        userModelsCollection.add(transform(user));
      }
    } else {
      userModelsCollection = Collections.emptyList();
    }

    return userModelsCollection;
  }
}
