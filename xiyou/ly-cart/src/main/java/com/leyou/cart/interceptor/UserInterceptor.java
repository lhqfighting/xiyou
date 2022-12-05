package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    private JwtProperties prop;

    public UserInterceptor(JwtProperties prop){
        this.prop = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取Cookie中的token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            //解析token
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            //传递user
            tl.set(userInfo);
            //放行
            return true;

        } catch (Exception e) {
            log.error("[购物车服务] 解析用户身份失败.",e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //用完数据，清空线程
        tl.remove();
    }

    public static UserInfo getUser(){
        return tl.get();
    }
}
