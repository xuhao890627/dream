package com.st.dream.exception;

public class ResourceExistsException extends DreamException {
    public ResourceExistsException(ErrorCodes code, String message) {
        super(code, message);
    }

    public ResourceExistsException(ErrorCodes code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
