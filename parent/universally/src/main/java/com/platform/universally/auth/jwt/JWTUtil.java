package com.platform.universally.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * jwt工具类型
 * Created by Administrator on 2018/12/24.
 */
public class JWTUtil {

    private final static long EXPIRE_TIME = 24 * 60 * 1000;

    private final static String JWT_SIGN = "65ythj3ewds7yuhj3ewdsiukjmws";

    public static String sign(String userName){
        long expire_time = System.currentTimeMillis() + EXPIRE_TIME;
        Algorithm algorithm = Algorithm.HMAC256(JWT_SIGN.getBytes());
        return JWT.create()
                .withClaim("userName", userName)
                .withExpiresAt(new Date(expire_time))
                .sign(algorithm);
    }


    public static boolean verify(String token, String userName) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SIGN.getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("userName", userName).build();
        verifier.verify(token);
        return true;
    }

    public static String getUserName(String token) {
        return JWT.decode(token).getClaim("userName").asString();
    }


}
