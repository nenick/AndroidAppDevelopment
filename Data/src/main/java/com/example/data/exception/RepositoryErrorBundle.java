/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.exception;

import com.example.shared.exception.ErrorBundle;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
public class RepositoryErrorBundle implements ErrorBundle {

    private Error error;
    private Exception exception;

    public RepositoryErrorBundle(Error error) {
        this.error = error;
    }

  public RepositoryErrorBundle(Error error, Exception exception) {
      this.error = error;
      this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

    @Override
    public Error getError() {
        return error;
    }

    public String getErrorMessage() {
    String message = "";
    if (this.exception != null) {
      this.exception.getMessage();
    }
    return message;
  }
}
