package com.ordersystem.siren.exception;

public class CafeNotFoundException extends RuntimeException {
    public CafeNotFoundException() {
    }

    public CafeNotFoundException(String message) {
        super(message);
    }

    public CafeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CafeNotFoundException(Throwable cause) {
        super(cause);
    }

    public CafeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
