package com.st.dream.exception;

public class DatabaseException extends DreamException {
    public DatabaseException(ErrorCodes code, String message) {
        super(code, message);
    }

    public DatabaseException(ErrorCodes code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
