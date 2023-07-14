package com.hilltop.user.controller;

import com.hilltop.user.domain.request.LoginRequestDto;
import com.hilltop.user.domain.request.UserRequestDto;
import com.hilltop.user.enums.ErrorMessage;
import com.hilltop.user.enums.SuccessMessage;
import com.hilltop.user.enums.UserType;
import com.hilltop.user.exception.HillTopUserApplicationException;
import com.hilltop.user.exception.TokenException;
import com.hilltop.user.service.JwtTokenService;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Auth controller test
 * Unit tests for {@link  AuthController}
 */
class AuthControllerTest {

    private static final String MOBILE_NO = "0779090909";
    private static final String PASSWORD = "password";
    private static final String FAILED = "Failed.";
    private final String LOGIN_URI = "/api/v1/auth/sign-in";
    private final String VALIDATE_TOKEN_URI = "/api/v1/auth/validate-token?token=123";
    private final UserRequestDto userRequestDto = getUserRequestDto();
    private final LoginRequestDto loginRequestDto = getLoginRequestDto();
    AuthController authController;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private AuthenticationManager authenticationManager;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        authController = new AuthController(authenticationManager, jwtTokenService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    /**
     * Unit tests for login() method.
     */
    @Test
    void Should_ReturnOk_When_LoginIsSuccessful() throws Exception {
        when(jwtTokenService.generateToken(anyString())).thenReturn("token123");
        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URI)
                        .content(loginRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_LOGGED_IN.getMessage()));
    }

    /**
     * Unit tests for validateToken() method.
     */
    @Test
    void Should_ReturnOk_When_ValidateTokenSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VALIDATE_TOKEN_URI)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.VALID_TOKEN.getMessage()));
    }

    /**
     * This method is used to mock userRequestDto.
     *
     * @return userRequestDto
     */
    private UserRequestDto getUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("User");
        userRequestDto.setMobileNo(MOBILE_NO);
        userRequestDto.setPassword(PASSWORD);
        userRequestDto.setUserType(UserType.USER);
        return userRequestDto;
    }

    /**
     * This method is used to mock loginRequestDto.
     *
     * @return loginRequestDto
     */
    private LoginRequestDto getLoginRequestDto() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setMobileNo(MOBILE_NO);
        loginRequestDto.setPassword(PASSWORD);
        return loginRequestDto;
    }
}