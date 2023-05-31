package com.hilltop.user.exception;

public class HillTopUserApplicationException extends RuntimeException{
    public HillTopUserApplicationException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
    public HillTopUserApplicationException(String errorMessage) {
        super(errorMessage);
    }
}
