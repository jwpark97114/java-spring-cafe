package com.codesquad.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ForbiddenAccessException.class)
    public String handleForbiddenAccess(ForbiddenAccessException exception, HttpServletRequest request, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        String referer = request.getHeader("Referer");
        String returnUrl = (referer != null) ? referer : "/";
        return "redirect:" + returnUrl;
    }


    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException authException, HttpServletRequest request ,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", authException.getMessage());
        String referer = request.getHeader("Referer");
        String returnUrl = (referer != null) ? referer : "/";
        return "redirect:" + returnUrl;
    }
}
