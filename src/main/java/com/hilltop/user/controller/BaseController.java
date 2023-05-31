package com.hilltop.user.controller;

import com.hilltop.user.domain.response.ResponseDto;
import com.hilltop.user.domain.response.ResponseWrapper;
import com.hilltop.user.enums.ErrorMessage;
import com.hilltop.user.enums.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Component
public class BaseController {

    protected ResponseEntity<ResponseWrapper> getSuccessResponse(SuccessMessage successMessage,
                                                                 ResponseDto responseDto, HttpStatus httpStatus) {
        ResponseWrapper responseWrapper = new ResponseWrapper(successMessage.getMessage(), responseDto);
        return new ResponseEntity<>(responseWrapper, httpStatus);
    }

    protected ResponseEntity<ResponseWrapper> getBadRequestErrorResponse(ErrorMessage errorMessage,
                                                                         HttpStatus httpStatus) {
        ResponseWrapper responseWrapper = new ResponseWrapper(errorMessage.getMessage());
        return new ResponseEntity<>(responseWrapper, httpStatus);
    }

    protected ResponseEntity<ResponseWrapper> getInternalServerError() {
        ResponseWrapper responseWrapper = new ResponseWrapper(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage());
        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
