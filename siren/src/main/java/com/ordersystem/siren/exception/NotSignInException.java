package com.ordersystem.siren.exception;

public class NotSignInException extends RuntimeException {
    public NotSignInException() {
    }

    public NotSignInException(String message) {
        super(message);
    }

    public NotSignInException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSignInException(Throwable cause) {
        super(cause);
    }

    public NotSignInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
