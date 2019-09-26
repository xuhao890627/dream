package com.st.dream.exception;

public class ConnectorException extends DreamException {

    private ServiceResponse reason;

    public ConnectorException(ErrorCodes code, String message) {
        super(code, message);
    }

    public ConnectorException(ErrorCodes code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ConnectorException(ErrorCodes code, String message, ServiceResponse reason) {
        super(code, message);
        this.reason = reason;
    }

    public ServiceResponse getReason() {
        return this.reason;
    }
}
