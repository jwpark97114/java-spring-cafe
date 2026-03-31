package com.codesquad.exceptions;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String errorMessage){
        super(errorMessage);
    }

}
