package io.gowalk.gowalk.exception;

public class GoWalkException extends RuntimeException {
    public GoWalkException() {
        super();
    }

    public GoWalkException(String message) {
        super(message);
    }

    public GoWalkException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoWalkException(Throwable cause) {
        super(cause);
    }
}
