package com.st.dream.exception;

public class BadRequestException extends DreamException{

    public BadRequestException(ErrorCodes code, String message) {
        super(code, message);
    }


}
