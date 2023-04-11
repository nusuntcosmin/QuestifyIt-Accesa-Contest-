package com.example.questifyit.exceptions;

public class NoMatchingPassword extends Exception{
    public NoMatchingPassword() {
    }

    public NoMatchingPassword(String message) {
        super(message);
    }

    public NoMatchingPassword(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMatchingPassword(Throwable cause) {
        super(cause);
    }

    public NoMatchingPassword(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
