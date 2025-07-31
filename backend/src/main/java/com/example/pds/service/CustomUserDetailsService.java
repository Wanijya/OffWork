package com.example.pds.service;

import com.example.pds.model.LoginUser;
import com.example.pds.repository.LoginUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Custom UserDetailsService for Spring Security
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;

    public CustomUserDetailsService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // Fetch LoginUser from the database by user_id
        LoginUser loginUser = loginUserRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        // Return our custom UserDetails object
        return new CustomUserDetails(loginUser);
    }
}
