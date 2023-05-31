package com.hilltop.user.controller;

import com.hilltop.user.domain.response.ResponseWrapper;
import com.hilltop.user.enums.ErrorMessage;
import com.hilltop.user.exception.HillTopUserApplicationException;
import com.hilltop.user.exception.TokenException;
import com.hilltop.user.exception.UserExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final BaseController baseController;

    public GlobalControllerExceptionHandler(BaseController baseController) {
        this.baseController = baseController;
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ResponseWrapper> userExistException(UserExistException exception) {
        log.debug("User already exist for mobileNo. {}", exception.getMessage());
        return baseController.getBadRequestErrorResponse(ErrorMessage.MOBILE_NO_EXIST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HillTopUserApplicationException.class)
    public ResponseEntity<ResponseWrapper> hillTopUserApplicationException(HillTopUserApplicationException exception) {
        log.debug("Internal Server Error. {}", exception.getMessage());
        return baseController.getInternalServerError();
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ResponseWrapper> tokenException(TokenException exception) {
        log.debug("Invalid Token. {}", exception.getMessage());
        return baseController.getBadRequestErrorResponse(ErrorMessage.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
    }
}