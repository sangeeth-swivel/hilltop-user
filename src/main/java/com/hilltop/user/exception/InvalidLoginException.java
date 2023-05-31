package com.hilltop.user.exception;

public class InvalidLoginException extends HillTopUserApplicationException{
    public InvalidLoginException(String errorMessage) {
        super(errorMessage);
    }
}
