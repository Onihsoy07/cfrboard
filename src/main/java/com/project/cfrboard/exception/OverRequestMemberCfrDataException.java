package com.project.cfrboard.exception;

public class OverRequestMemberCfrDataException extends RuntimeException {
    public OverRequestMemberCfrDataException() {
    }

    public OverRequestMemberCfrDataException(String message) {
        super(message);
    }

    public OverRequestMemberCfrDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverRequestMemberCfrDataException(Throwable cause) {
        super(cause);
    }
}
