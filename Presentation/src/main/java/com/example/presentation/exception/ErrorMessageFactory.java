/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.presentation.exception;

import android.content.Context;

import com.example.shared.exception.ErrorBundle;
import com.example.presentation.R;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

   private enum ErrorMessage {

       Unexpected(ErrorBundle.Error.UnexpectedException, R.string.exception_message_generic),
       NoInternetConnection(ErrorBundle.Error.NetworkConnection, R.string.exception_message_no_connection),
       UserNotFound(ErrorBundle.Error.UserNotFound, R.string.exception_message_user_not_found);

       final ErrorBundle.Error error;
       final int message;

       ErrorMessage(ErrorBundle.Error error, int message) {
           this.error = error;
           this.message = message;
       }
   }

  private ErrorMessageFactory() {
    //empty
  }

  /**
   * Creates a String representing an error message.
   *
   * @param context Context needed to retrieve string resources.
   * @param exception An com.example.data.exception used as a condition to retrieve the correct error message.
   * @return {@link String} an error message.
   */
  public static String create(Context context, ErrorBundle.Error exception) {
      for (ErrorMessage errorMessage : ErrorMessage.values()) {
          if(errorMessage.error.equals(exception)) {
              return context.getString(errorMessage.message);
          }
      }
      return context.getString(ErrorMessage.Unexpected.message);
  }
}
