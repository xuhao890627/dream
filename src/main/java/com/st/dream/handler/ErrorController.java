package com.st.dream.handler;

import com.st.dream.exception.ErrorResponse;
import com.st.dream.exception.GeneralErrorCodes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {

    @RequestMapping(value = "/403")
    public ErrorResponse accessDenied(HttpServletRequest request) {
        return new ErrorResponse(GeneralErrorCodes.ERROR_403, "User does not have appropriate authorization", request.getAttribute("requestID").toString());
    }
}
