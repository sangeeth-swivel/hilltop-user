package com.hilltop.user.service;

import com.hilltop.user.domain.entity.User;
import com.hilltop.user.domain.request.UserRequestDto;
import com.hilltop.user.enums.UserType;
import com.hilltop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserRequestDto userRequestDto;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userRequestDto = new UserRequestDto();
        userRequestDto.setName("user");
        userRequestDto.setMobileNo("0775544651");
        userRequestDto.setPassword("test123");
        userRequestDto.setUserType(UserType.ADMIN);
    }

    @Test
    void Should_ReturnLoadUserByUsername_ValidUser() {
        String username = "user";
        User user = new User(userRequestDto);
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByMobileNo(username)).thenReturn(optionalUser);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(user.getMobileNo(), userDetails.getUsername(), "UserDetails username should match the provided username");
    }
}