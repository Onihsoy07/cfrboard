package com.project.cfrboard.exception;

public class NoBoardTableException extends RuntimeException {
    public NoBoardTableException(String message) {
        super(message);
    }

    public NoBoardTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoBoardTableException(Throwable cause) {
        super(cause);
    }

    public NoBoardTableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
