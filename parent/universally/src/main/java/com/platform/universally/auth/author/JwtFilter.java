package com.platform.universally.auth.author;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMethod;·

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

/**
 * shiro 认证拦截器
 * Created by Administrator on 2018/12/24.
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static String LOGIN_SIGN = "token";

    /**
     * 尝试登录
     * 判断对应的token是否为空，如果为空则直接返回登录失败
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(LOGIN_SIGN);//获取当前的token
        return StringUtils.isNoneBlank(authorization);//当有token是，尝试登录
//        Subject subject = getSubject(request, response);

        //调用的应该是securityRealm中的doGetAuthenticationInfo方法
//        return !subject.isAuthenticated();//在用户身份校验完成之后才会更新当前的状态
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(LOGIN_SIGN);
        JWTToken token = new JWTToken(header);
        //判断当前的token是否为用户登录后从后台返回的数据
        //jwt生成的token中包含了用户名和密码在用户登录时也将对应的用户名和密码使用jwt封装为token这样就可以完成登录验证了
        Subject subject = getSubject(request, response);
        Object obj = subject.getPrincipal();//从当前的subject中获取对应的用户名及密码
        String userName = (String) obj;
        token.setUsername(userName);
        subject.login(token);
        return true;
    }


    /**
     * 在pre
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 判断是否已经完成登录，这种方式错误，
        // 不能只是根据当前是否有对应的token就接受是否已经完成登录
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对跨域提供支持
     * @param request 请求参数
     * @param response 响应参数
     * @return
     * @throws Exception
     */
    /*@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }*/
}
