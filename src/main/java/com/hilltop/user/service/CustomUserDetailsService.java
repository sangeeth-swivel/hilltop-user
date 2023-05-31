package com.hilltop.user.service;

import com.hilltop.user.configuration.CustomUserDetails;
import com.hilltop.user.domain.entity.User;
import com.hilltop.user.exception.InvalidLoginException;
import com.hilltop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByMobileNo(username);
        return optionalUser.map(CustomUserDetails::new)
                .orElseThrow(() -> new InvalidLoginException("User not found for username: " + username));
    }
}
