package com.st.dream.service;

import com.st.dream.common.ServerResponse;
import com.st.dream.exception.DreamException;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    ServerResponse createToken();

    void checkToken(HttpServletRequest request) throws DreamException;
}
