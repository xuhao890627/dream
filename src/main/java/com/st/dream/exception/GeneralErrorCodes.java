package com.st.dream.exception;

public enum GeneralErrorCodes implements ErrorCodes{
    //General errors
    ERROR_400(400,"Bad request"),
    ERROR_403(403,"Unauthorized"),
    ERROR_404(404,"Resource not found"),
    ERROR_500(500,"Internal server error"),
    ERROR_604(604,"Resource already exists"),
    ERROR_605(605, "DataPipe connection failed");

    private Integer code;
    private String usage;

    GeneralErrorCodes(Integer code,String usage) {
        this.code = code;
        this.usage = usage;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
