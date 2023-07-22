package com.hilltop.user.controller;

import com.hilltop.user.domain.request.UserRequestDto;
import com.hilltop.user.domain.response.ResponseWrapper;
import com.hilltop.user.enums.ErrorMessage;
import com.hilltop.user.enums.SuccessMessage;
import com.hilltop.user.enums.UserType;
import com.hilltop.user.exception.HillTopUserApplicationException;
import com.hilltop.user.exception.UserExistException;
import com.hilltop.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User controller test
 * Unit tests for {@link  UserController}
 */
class UserControllerTest {

    private static final String MOBILE_NO = "0779090909";
    private static final String PASSWORD = "password";
    private static final String FAILED = "Failed.";
    private final String REGISTER_URI = "/api/v1/user";
    private final UserRequestDto userRequestDto = getUserRequestDto();
    UserController userController;
    @Mock
    private UserService userService;
    private MockMvc mockMvc;
    @Mock
    private BaseController baseController;
    @InjectMocks
    private GlobalControllerExceptionHandler globalControllerExceptionHandler;
    @BeforeEach
    void setUp() {
        openMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * Unit tests for registerUser() method.
     */
    @Test
    void Should_ReturnOk_When_RegisterUserIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_URI)
                        .content(userRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_ADDED.getMessage()));
    }

    @Test
    void Should_ReturnBadRequest_When_MissingRequiredFields() throws Exception {
        UserRequestDto requestDto = userRequestDto;
        requestDto.setPassword(null);
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_URI)
                        .content(requestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessage.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void Should_ReturnBadRequest_When_MobileNoDoesntMatchThePattern() throws Exception {
        UserRequestDto requestDto = userRequestDto;
        requestDto.setMobileNo("077123*");
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_URI)
                        .content(requestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessage.INVALID_MOBILE_NO.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
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

    @Test
    void Should_ReturnInternalServerError_When_BookingIsFailedDueToInternalErrors(){
        HillTopUserApplicationException exception = new HillTopUserApplicationException("Failed.");
        ResponseEntity<ResponseWrapper> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(baseController.getInternalServerError()).thenReturn(expectedResponse);

        ResponseEntity<ResponseWrapper> actualResponse = globalControllerExceptionHandler.hillTopUserApplicationException(exception);

        verify(baseController, times(1)).getInternalServerError();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }
}