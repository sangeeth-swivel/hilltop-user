package com.hilltop.user.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto implements RequestDto {

    private static final String MOBILE_NO_PATTERN = "^\\d{10}$";
    private String mobileNo;
    private String password;
    @Override
    public boolean isRequiredFieldsAvailable() {
        return isNonEmpty(mobileNo) && isNonEmpty(password);
    }

    public boolean isValidMobileNo() {
        return mobileNo.matches(MOBILE_NO_PATTERN);
    }
}
