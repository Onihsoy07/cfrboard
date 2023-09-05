package com.project.cfrboard.exception;

public class NotMatchMemberDataException extends RuntimeException {
    public NotMatchMemberDataException(String message) {
        super(message);
    }

    public NotMatchMemberDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMatchMemberDataException(Throwable cause) {
        super(cause);
    }

    public NotMatchMemberDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
