package com.project.cfrboard.exception;

public class OverRequestCfrData extends RuntimeException {
    public OverRequestCfrData() {
    }

    public OverRequestCfrData(String message) {
        super(message);
    }

    public OverRequestCfrData(String message, Throwable cause) {
        super(message, cause);
    }

    public OverRequestCfrData(Throwable cause) {
        super(cause);
    }
}
