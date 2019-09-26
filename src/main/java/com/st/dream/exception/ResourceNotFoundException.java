package com.st.dream.exception;

public class ResourceNotFoundException extends DreamException {


    public ResourceNotFoundException(ErrorCodes code, String message) {
        super(code, message);
    }

    public ResourceNotFoundException(ErrorCodes code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
