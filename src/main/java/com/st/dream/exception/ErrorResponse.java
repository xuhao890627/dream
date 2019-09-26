package com.st.dream.exception;

public class ErrorResponse {

    private ErrorCodes code;
    private String message;
    private String requestID;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCodes code, String message, String requestID) {
        this.code = code;
        this.message = message;
        this.requestID = requestID;
    }

    public ErrorCodes getCode() {
        return code;
    }

    public void setCode(ErrorCodes code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
