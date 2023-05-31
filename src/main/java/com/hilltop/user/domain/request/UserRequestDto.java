package com.hilltop.user.domain.request;

import com.hilltop.user.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UserRequestDto extends LoginRequestDto {

    private String name;
    private UserType userType;
    @Override
    public boolean isRequiredFieldsAvailable() {
        return isNonEmpty(name) && super.isRequiredFieldsAvailable();
    }
}
