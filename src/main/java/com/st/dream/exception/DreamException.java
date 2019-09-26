package com.st.dream.exception;

public class DreamException extends Exception {
    private ErrorCodes code;


    public DreamException(ErrorCodes code, String message) {
        super(message);
        this.code = code;
    }

    public DreamException(ErrorCodes code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ErrorCodes getCode() {
        return this.code;
    }

    public void setCode(ErrorCodes code) {
        this.code = code;
    }
}
