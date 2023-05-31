package com.hilltop.user.domain.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilltop.user.exception.HillTopUserApplicationException;

public interface RequestDto {

    boolean isRequiredFieldsAvailable();

    default boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }

    default String toLogJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new HillTopUserApplicationException("Json conversion was failed.", e);
        }
    }
}
