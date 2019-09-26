package com.st.dream.handler;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.st.dream.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(value = {DatabaseException.class})
    protected ResponseEntity<Object> handleException(DatabaseException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        response.setRequestID((String)request.getAttribute("requestID",0));

        logger.error("Catching Exception ", ex);
        return handleExceptionInternal(ex, response,
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {DreamException.class})
    protected ResponseEntity<Object> handleException(DreamException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        response.setRequestID((String) request.getAttribute("requestID", 0));
        logger.error("Catching Exception", ex);
        if (ex instanceof ResourceNotFoundException) {
            return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        } else if (ex instanceof ResourceExistsException) {
            return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
        } else if (ex instanceof BadRequestException) {
            return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }
//        else if (ex instanceof ConnectorException) {
//            ConnectorException connectorException = (ConnectorException) ex;
//            if (connectorException.getReason() != null && connectorException.getReason().getStatusCode() != null) {
//                HttpStatus status = HttpStatus.resolve(connectorException.getReason().getStatusCode());
//                if (status != null) {
//                    try {
//                        OperationResponse operationResponse = objectMapper.readValue(connectorException.getReason().getResponse(), OperationResponse.class);
//                        if (operationResponse.getMessage() != null) {
//                            logger.error("ConnectorException root cause: {}", operationResponse.getMessage());
//                        }
//                        return handleExceptionInternal(ex, response,
//                            new HttpHeaders(), status, request);
//                    } catch (IOException e) {
//                        logger.warn("Unable to map ConnectorException response: {}", connectorException.getReason().getResponse());
//                        return handleExceptionInternal(ex, response,
//                            new HttpHeaders(), status, request);
//                    }
//                } else {
//                    logger.warn("ConnectorException received invalid status code: {}", connectorException.getReason().getStatusCode());
//                }
//            }
//            return handleExceptionInternal(ex, response,
//                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//        }
        else {
            return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }


    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<String>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        String requestID = (String)request.getAttribute("requestID",0);

        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_400, errors.toString(), requestID), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationServiceException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationServiceException(AuthenticationServiceException ex, HttpServletRequest request) {

        logger.error("Exception Catch all: " + ex.getClass().getSimpleName(),ex);
        if (ex instanceof RuntimeException) {
            return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_500,"Internal Server Error", (String)request.getAttribute("requestID")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_403,ex.getMessage(),(String)request.getAttribute("requestID")), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {

        logger.error("Exception Catch all: " + ex.getClass().getSimpleName(),ex);
        if (ex instanceof RuntimeException) {
            return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_500,"Internal Server Error", (String)request.getAttribute("requestID")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_500,ex.getMessage(),(String)request.getAttribute("requestID")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("Catching Exception from {}: ", ex.getClass().getSimpleName() ,ex);
        String requestID = (String)request.getAttribute("requestID",0);
        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_400,ex.getMessage(), requestID), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("Catching Exception from {}: ", ex.getClass().getSimpleName() ,ex);
        String requestID = (String)request.getAttribute("requestID",0);
        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_400,ex.getMessage(), requestID), HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("Catching Exception from {}: ", ex.getClass().getSimpleName() ,ex);
        String requestID = (String)request.getAttribute("requestID",0);
        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_400,ex.getMessage(),requestID), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("Catching Exception from {}: ", ex.getClass().getSimpleName() ,ex);
        String requestID = (String)request.getAttribute("requestID",0);
        String errorMessage = ex.getMessage();
        if (ex.getRequiredType() != null && ex.getRequiredType().getEnumConstants() != null) {
            errorMessage = ex.getValue() + " is not valid, expected: " + Arrays.toString(ex.getRequiredType().getEnumConstants());
        }
        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_400,errorMessage, requestID), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<String>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }

        String requestID = (String)request.getAttribute("requestID",0);

        return new ResponseEntity<Object>(new ErrorResponse(GeneralErrorCodes.ERROR_400, errors.toString(), requestID), HttpStatus.BAD_REQUEST);
    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("Catching Exception from {}: ", ex.getClass().getSimpleName() ,ex);
        String requestID = (String)request.getAttribute("requestID",0);
        String payload = "There is something wrong with the payload";
        if(ex.getCause() != null && ex.getCause().getStackTrace() != null) {
            if (ex.getCause() instanceof InvalidFormatException) {
                InvalidFormatException ife = (InvalidFormatException)ex.getCause();
                payload = String.format("Invalid value %s for field '%s', the correct type is %s", ife.getValue(), getActualFieldName(ife.getPath()), ife.getTargetType().getSimpleName());
            } else if(ex.getCause() instanceof UnrecognizedPropertyException) {
                UnrecognizedPropertyException urpe = (UnrecognizedPropertyException)ex.getCause();
                payload = String.format("Field %s is unrecognized.", urpe.getPropertyName());
            } else if(ex.getCause() instanceof MismatchedInputException) {
                payload = String.format("Field %s is of the wrong type", ((MismatchedInputException) ex.getCause()).getTargetType().getSimpleName());
            }
        }

        return new ResponseEntity<>(new ErrorResponse(GeneralErrorCodes.ERROR_400,payload, requestID), HttpStatus.BAD_REQUEST);
    }

    private String getActualFieldName(List<JsonMappingException.Reference> path) {
        StringJoiner joiner = new StringJoiner(".");
        if(path != null && !path.isEmpty()) {
            for (JsonMappingException.Reference reference : path) {
                joiner.add(reference.getFieldName() == null? String.valueOf(reference.getIndex()) : reference.getFieldName());
            }

        }

        return joiner.toString();
    }

}
