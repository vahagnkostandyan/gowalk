package io.gowalk.gowalk.exception;

public class FindStoryException extends GoWalkException {
    private static final String DEFAULT_MESSAGE = "Error occurred while finding story.";

    public FindStoryException() {
        super(DEFAULT_MESSAGE);
    }

    public FindStoryException(String message) {
        super(message);
    }

    public FindStoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindStoryException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
