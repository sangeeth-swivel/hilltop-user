package com.hilltop.user.request;

import com.hilltop.user.domain.request.LoginRequestDto;
import com.hilltop.user.exception.InvalidLoginException;
import com.hilltop.user.exception.TokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Login requestDto test
 * Unit tests for {@link  LoginRequestDto}
 */
class LoginRequestDtoTest {

    private LoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setMobileNo("779090909");
        loginRequestDto.setPassword("password");
    }

    /**
     * Unit tests for isRequiredFieldsAvailable() method
     */
    @Test
    void Should_ReturnTrue_When_RequiredFieldsAreAvailable() {
        assertTrue(loginRequestDto.isRequiredFieldsAvailable());
    }

    @Test
    void Should_ReturnFalse_When_RequiredFieldsAreMissing() {
        loginRequestDto.setPassword(null);
        assertFalse(loginRequestDto.isRequiredFieldsAvailable());
    }

    @Test
    void Should_ReturnErrorMessageAndInvalidLoginException() {
        String errorMessage = "Invalid login attempt";
        InvalidLoginException exception = new InvalidLoginException(errorMessage);
        assertEquals(errorMessage, exception.getMessage(), "Error message should match");
    }

    @Test
    void Should_ReturnErrorMessageAndThrowableInvalidLoginException() {
        String errorMessage = "Token validation failed";
        Throwable cause = new RuntimeException("Underlying cause of the token error");
        TokenException exception = new TokenException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage(), "Error message should match");
        assertEquals(cause, exception.getCause(), "Throwable cause should match");
    }
}