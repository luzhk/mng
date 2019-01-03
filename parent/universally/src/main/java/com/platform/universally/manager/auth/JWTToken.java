package com.platform.universally.manager.auth;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 认证的分类
 * Created by Administrator on 2018/12/24.
 */
public class JWTToken extends UsernamePasswordToken {
    // 密钥
    private String token;


    public JWTToken(String token) {
        this.token = token;
    }


    public JWTToken(String token, String username, String password) {
        super(username, password.toCharArray());
        this.token = token;
    }


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
