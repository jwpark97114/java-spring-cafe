package com.codesquad.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if(!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlermethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlermethod.getMethodAnnotation(LoginRequired.class);

        if (loginRequired != null) {
            User user = (User) request.getSession().getAttribute("currentUser");
            if (user == null) {
                // Manually creating the FlashAttribute
                FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
                flashMap.put("errorMessage", loginRequired.message());

                // Registering the FlashMap so it survives the redirect
                FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
                flashMapManager.saveOutputFlashMap(flashMap, request, response);

                response.sendRedirect("/user/login");
                return false;
            }
        }
        return true;
    }
}
