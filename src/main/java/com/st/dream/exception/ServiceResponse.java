package com.st.dream.exception;

public class ServiceResponse {

    private Integer statusCode;
    private String response;

    public ServiceResponse(Integer statusCode, String response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
