package com.example.presentation;

import com.example.shared.exception.ErrorBundle;

public class TestErrorBundle implements ErrorBundle {

    private Exception exception = new NullPointerException();
    private Error error = Error.UnexpectedException;

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public Error getError() {
        return error;
    }

    public ErrorBundle withError(Error error) {
        this.error = error;
        return this;
    }

    @Override
    public String getErrorMessage() {
        return "Dummy error Message";
    }
}
