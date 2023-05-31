package com.hilltop.user.exception;

public class TokenException extends HillTopUserApplicationException{
    public TokenException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
