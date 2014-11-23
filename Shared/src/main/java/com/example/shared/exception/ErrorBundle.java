/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.shared.exception;

/**
 * Interface to represent a wrapper around an {@link java.lang.Exception} to manage errors.
 */
public interface ErrorBundle {

    enum Error {
        UnexpectedException,
        NetworkConnection,
        UserNotFound
    }

    Exception getException();

    Error getError();

    String getErrorMessage();
}
