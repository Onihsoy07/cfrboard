package com.project.cfrboard.exception;

public class NotMasterOfDataException extends RuntimeException {
    public NotMasterOfDataException() {
    }

    public NotMasterOfDataException(String message) {
        super(message);
    }

    public NotMasterOfDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMasterOfDataException(Throwable cause) {
        super(cause);
    }
}
