package com.hilltop.user.domain.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilltop.user.exception.HillTopUserApplicationException;

public interface ResponseDto {
    default String toLogJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new HillTopUserApplicationException("Json conversion was failed.", e);
        }
    }
}
