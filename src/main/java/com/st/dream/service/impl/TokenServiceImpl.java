package com.st.dream.service.impl;

import cn.hutool.core.text.StrBuilder;
import com.st.dream.common.Constant;
import com.st.dream.common.ResponseCode;
import com.st.dream.common.ServerResponse;
import com.st.dream.exception.*;
import com.st.dream.service.TokenService;
import com.st.dream.utils.JedisUtil;
import com.st.dream.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.generateStr(24);
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

        return ServerResponse.success(token.toString());
    }

    @Override
    public void checkToken(HttpServletRequest request) throws DreamException {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isEmpty(token)) {// parameter中也不存在token
                throw new BadRequestException(GeneralErrorCodes.ERROR_400,ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new ResourceNotFoundException(GeneralErrorCodes.ERROR_400,ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }
}
