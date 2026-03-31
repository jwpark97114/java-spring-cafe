package com.codesquad.exceptions;

public class ForbiddenAccessException  extends RuntimeException{
    public ForbiddenAccessException(String message){
        super(message);
    }
}
