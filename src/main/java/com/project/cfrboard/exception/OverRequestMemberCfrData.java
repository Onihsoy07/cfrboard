package com.project.cfrboard.exception;

public class OverRequestMemberCfrData extends RuntimeException {
    public OverRequestMemberCfrData() {
    }

    public OverRequestMemberCfrData(String message) {
        super(message);
    }

    public OverRequestMemberCfrData(String message, Throwable cause) {
        super(message, cause);
    }

    public OverRequestMemberCfrData(Throwable cause) {
        super(cause);
    }
}
