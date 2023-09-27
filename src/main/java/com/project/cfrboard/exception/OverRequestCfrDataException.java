package com.project.cfrboard.exception;

public class OverRequestCfrDataException extends RuntimeException {
    public OverRequestCfrDataException() {
    }

    public OverRequestCfrDataException(String message) {
        super(message);
    }

    public OverRequestCfrDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverRequestCfrDataException(Throwable cause) {
        super(cause);
    }
}
