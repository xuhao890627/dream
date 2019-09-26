package com.st.dream.exception;


public enum ConnectorErrorCodes implements ErrorCodes {
    UNAUTHORIZED(9000, "Failed to authenticate with internal service."),
    SERVICE_ERROR(9001, "Service Call failed."),
    INVALID_CONFIG(9002, "Configuration failed to create a URI"),
    BAD_REQUEST(90003, "Bad request sent to internal service"),
    CONNECTION_ERROR(90004, "Filed to establish connection"),
    FORBIDDEN(9005, "User is Forbidden");

    private Integer code;
    private String usage;

    private ConnectorErrorCodes(Integer code, String usage) {
        this.code = code;
        this.usage = usage;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getUsage() {
        return this.usage;
    }

}
