package com.st.dream.utils;

        import com.auth0.jwt.JWT;
        import com.auth0.jwt.algorithms.Algorithm;
        import com.st.dream.sbmybatis.model.User;

public class TokenUtil {

    private  TokenUtil(){}

    public static String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
