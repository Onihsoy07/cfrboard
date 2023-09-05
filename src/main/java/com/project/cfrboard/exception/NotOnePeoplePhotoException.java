package com.project.cfrboard.exception;

public class NotOnePeoplePhotoException extends RuntimeException {
    public NotOnePeoplePhotoException(String message) {
        super(message);
    }

    public NotOnePeoplePhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotOnePeoplePhotoException(Throwable cause) {
        super(cause);
    }

    public NotOnePeoplePhotoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
