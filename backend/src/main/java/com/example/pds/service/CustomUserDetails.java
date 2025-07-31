package com.example.pds.service;

import com.example.pds.model.LoginUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// Custom UserDetails implementation to hold additional user information
public class CustomUserDetails implements UserDetails {

    private String userId;
    private String password;
    private String userRole; // Raw role from DB (e.g., "1", "4", "8")
    private String distCode;
    // private String status; // Status field ab yahan store karne ki zarurat nahi hai agar check nahi karna

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(LoginUser user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userRole = user.getUserRole(); // Store the raw user role
        this.distCode = user.getDistCode(); // Store the district code
        // this.status = user.getStatus() != null ? user.getStatus().trim() : null; // Status ab store nahi hoga

        // Debugging logs (optional, can be removed)
        System.out.println("DEBUG: CustomUserDetails constructor for userId: " + this.userId);
        // System.out.println("DEBUG: Raw Status from DB: '" + user.getStatus() + "'"); // No longer needed
        // System.out.println("DEBUG: Trimmed Status stored: '" + this.status + "'"); // No longer needed

        // Map the database role to Spring Security's GrantedAuthority
        String roleName = "ROLE_UNKNOWN"; // Default role if no match
        if ("1".equals(user.getUserRole())) {
            roleName = "ROLE_ADMIN"; // Admin user
        } else if ("4".equals(user.getUserRole())) {
            roleName = "ROLE_HO_USER"; // Head Office user
        } else if ("8".equals(user.getUserRole())) {
            roleName = "ROLE_DEPOT_USER"; // Depot-wise user
        }
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    // Custom getters for userRole and distCode
    public String getUserRole() {
        return userRole;
    }

    public String getDistCode() {
        return distCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming accounts do not expire
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming accounts are not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming credentials do not expire
    }

    @Override
    public boolean isEnabled() {
        // IMPORTANT: This now always returns true, bypassing any status check.
        // This means any user with correct credentials can log in, regardless of their 'status' in the database.
        // This is a SECURITY RISK if you intend to disable users.
        System.out.println("DEBUG: isEnabled() called for userId: " + this.userId + ". Always returning true (status check bypassed).");
        return true;
    }
}
