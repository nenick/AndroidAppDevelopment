/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.userlist;

/**
 * Class that represents a user in the presentation layer.
 */
public class UserModel {

  private final int userId;

  public UserModel(int userId) {
    this.userId = userId;
  }

  private String coverUrl;
  private String fullName;
  private String email;
  private String description;
  private int followers;

  public int getUserId() {
    return userId;
  }


  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

}
