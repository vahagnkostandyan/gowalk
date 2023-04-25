package io.gowalk.gowalk.exception;

public class GetRequestHistoryException extends GoWalkException {
    private static final String DEFAULT_MESSAGE = "Error occurred while getting user request(s) history(es).";

    public GetRequestHistoryException() {
        super(DEFAULT_MESSAGE);
    }

    public GetRequestHistoryException(String message) {
        super(message);
    }

    public GetRequestHistoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetRequestHistoryException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
