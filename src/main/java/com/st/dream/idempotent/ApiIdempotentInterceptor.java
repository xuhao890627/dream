package com.st.dream.idempotent;

import com.st.dream.exception.DreamException;
import com.st.dream.service.TokenService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//实现思路
//        为需要保证幂等性的每一次请求创建一个唯一标识token, 先获取token, 并将此token存入redis, 请求接口时, 将此token放到header或者作为请求参数请求接口, 后端接口判断redis中是否存在此token:
//
//        如果存在, 正常处理业务逻辑, 并从redis中删除此token, 那么, 如果是重复请求, 由于token已被删除, 则不能通过校验, 返回请勿重复操作提示
//        如果不存在, 说明参数不合法或者是重复请求, 返回提示即可
//  这种方式需要前台先调用一次生成token，生成token的同时将token扔入redis 然后请求的时候带上这个token

//  一般是通过url + userId + payload 组成下 通过MD5加密 存入redis中 设置这个key存在的时间在1s 如果1s内重复得到这个key 直接就返回  存在就说明重复提交了，直接返回了

public class ApiIdempotentInterceptor  implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws DreamException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (methodAnnotation != null) {
            checkApiIdempotent(request);// 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
        }

        return true;
    }

    private void checkApiIdempotent(HttpServletRequest request) throws DreamException {
        tokenService.checkToken(request);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
