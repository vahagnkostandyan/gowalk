package io.gowalk.gowalk.exception;

public class UserCreatingException extends GoWalkException {
    private static final String DEFAULT_MESSAGE = "Error occurred while creating user.";

    public UserCreatingException() {
        super(DEFAULT_MESSAGE);
    }

    public UserCreatingException(String message) {
        super(message);
    }

    public UserCreatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCreatingException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
