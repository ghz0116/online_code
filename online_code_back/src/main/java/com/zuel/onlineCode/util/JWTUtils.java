package com.zuel.onlineCode.util;

import com.auth0.jwt.JWT;


import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt操作的工具类
 */
@Component
public class JWTUtils {
    private static final String SECRET = "zuelYuanGuang";//密钥需要对外保密
    /**
     * 生成token信息
     *
     * @return 返回token
     */
    public static String createToken(HashMap<String, String> map, Date expiresAt) {
        JWTCreator.Builder builder = JWT.create();
        //设置payload
        map.forEach(builder::withClaim);
        //设置过期时间
        Map<String, Object> header = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return builder.withHeader(header)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 校验token是否合法
     * 如果非法会抛出相关的异常信息
     * 如果是合法的就会返回DecodedJWT对象
     *
     * @param token 传入token
     * @return 返回DecodedJWT对象
     */
    public static DecodedJWT verify(String token) {
        //如果不抛出异常说明验证通过，否则验证失败
        DecodedJWT verify;
        try {
            verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return verify;
    }
}
