package io.gowalk.gowalk.exception;

public class CreateRequestHistoryException extends GoWalkException {
    private static final String DEFAULT_MESSAGE = "Error occurred while creating user request history.";

    public CreateRequestHistoryException() {
        super(DEFAULT_MESSAGE);
    }

    public CreateRequestHistoryException(String message) {
        super(message);
    }

    public CreateRequestHistoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateRequestHistoryException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
