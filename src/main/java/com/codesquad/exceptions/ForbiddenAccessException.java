package com.codesquad.exceptions;

public class ForbiddenAccessException  extends RuntimeException{
    private final String redirectUrl;
    public ForbiddenAccessException(String url, String message){
        super(message);
        this.redirectUrl = url;
    }

    public String getRedirectUrl(){
        return this.redirectUrl;
    }
}
