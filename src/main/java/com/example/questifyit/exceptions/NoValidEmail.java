package com.example.questifyit.exceptions;

public class NoValidEmail extends Exception{
    public NoValidEmail() {
    }

    public NoValidEmail(String message) {
        super(message);
    }

    public NoValidEmail(String message, Throwable cause) {
        super(message, cause);
    }

    public NoValidEmail(Throwable cause) {
        super(cause);
    }

    public NoValidEmail(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
