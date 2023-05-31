package com.hilltop.user.exception;

public class UserExistException extends HillTopUserApplicationException{

    public UserExistException(String errorMessage) {
        super(errorMessage);
    }
}
