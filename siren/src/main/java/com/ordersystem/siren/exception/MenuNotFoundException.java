package com.ordersystem.siren.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException() {
    }

    public MenuNotFoundException(String message) {
        super(message);
    }

    public MenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MenuNotFoundException(Throwable cause) {
        super(cause);
    }

    public MenuNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
