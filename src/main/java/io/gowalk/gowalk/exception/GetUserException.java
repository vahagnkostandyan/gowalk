package io.gowalk.gowalk.exception;

public class GetUserException extends GoWalkException {
    private static final String DEFAULT_MESSAGE = "Error occurred while getting user.";

    public GetUserException() {
        super(DEFAULT_MESSAGE);
    }

    public GetUserException(String message) {
        super(message);
    }

    public GetUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetUserException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
