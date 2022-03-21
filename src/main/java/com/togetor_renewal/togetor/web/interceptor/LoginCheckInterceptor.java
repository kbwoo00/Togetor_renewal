package com.togetor_renewal.togetor.web.interceptor;

import com.togetor_renewal.togetor.web.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(Const.LOGIN_SESSION) == null){
            response.sendRedirect("/user/login?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }
}
