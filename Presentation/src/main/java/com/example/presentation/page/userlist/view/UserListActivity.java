/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.page.userlist.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.example.presentation.R;
import com.example.presentation.base.view.BaseActivity;
import com.example.presentation.navigation.Navigator;

/**
 * Activity that shows a list of Users.
 */
public class UserListActivity extends BaseActivity implements UserListFragment.UserListListener {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, UserListActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_user_list);

    this.initialize();
  }

  @Override public void onUserClicked(UserModel userModel) {
    this.navigator.navigateToUserDetails(this, userModel.getUserId());
  }

  /**
   * Initializes activity's private members.
   */
  private void initialize() {
    //This initialization should be avoided by using a dependency injection framework.
    //But this is an example and for testing purpose.
    this.navigator = new Navigator();
  }
}
