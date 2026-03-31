package com.codesquad.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ForbiddenAccessException.class)
    public String handleForbiddenAccess(ForbiddenAccessException exception, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        String returnUrl = (exception.getRedirectUrl() != null) ? exception.getRedirectUrl() : "/qna/";
        return "redirect:" + returnUrl;
    }
}
