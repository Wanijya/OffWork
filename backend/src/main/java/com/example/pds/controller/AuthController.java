package com.example.pds.controller;

import com.example.pds.service.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// Authentication related endpoints controller
@RestController
@RequestMapping("/api/dashboard")
public class AuthController {

    // This endpoint is handled by Spring Security's formLogin.
    // Custom logic can be added here if needed.
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Backend /login successful for user: " + authentication.getName());
        return ResponseEntity.ok("Login successful for user: " + authentication.getName());
    }

    // Endpoint to provide information about the current logged-in user
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("\n--- DEBUG: Backend /user-info called ---");
        System.out.println("DEBUG: Current Authentication object: " + authentication);

        if (authentication == null) {
            System.out.println("DEBUG: Authentication object is NULL. Returning 401.");
            return ResponseEntity.status(401).body("Not authenticated: Authentication object is null.");
        }

        System.out.println("DEBUG: Authentication.isAuthenticated(): " + authentication.isAuthenticated());
        System.out.println("DEBUG: Authentication.getName(): " + authentication.getName());
        System.out.println("DEBUG: Authentication.getPrincipal() type: " + authentication.getPrincipal().getClass().getName());
        System.out.println("DEBUG: Authentication.getPrincipal() value: " + authentication.getPrincipal());
        System.out.println("DEBUG: Authentication.getAuthorities(): " + authentication.getAuthorities());


        // Check if the user is truly authenticated (not anonymous)
        // This handles various forms of anonymous principals
        boolean isTrulyAuthenticated = false;
        Object principal = authentication.getPrincipal();

        if (authentication.isAuthenticated()) {
            if (principal instanceof String) {
                if ("anonymousUser".equals(principal)) {
                    System.out.println("DEBUG: Principal is String 'anonymousUser'. Not truly authenticated.");
                    isTrulyAuthenticated = false;
                } else {
                    // This case might mean a custom string principal, treat as authenticated for now
                    System.out.println("DEBUG: Principal is String (not anonymousUser). Potentially authenticated.");
                    isTrulyAuthenticated = true;
                }
            } else if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if ("anonymousUser".equals(userDetails.getUsername())) {
                    System.out.println("DEBUG: Principal is UserDetails 'anonymousUser'. Not truly authenticated.");
                    isTrulyAuthenticated = false;
                } else {
                    System.out.println("DEBUG: Principal is UserDetails (not anonymousUser). Truly authenticated.");
                    isTrulyAuthenticated = true;
                }
            } else {
                // Other types of principals, might be authenticated
                System.out.println("DEBUG: Principal is neither String nor UserDetails. Type: " + principal.getClass().getName() + ". Potentially authenticated.");
                isTrulyAuthenticated = true;
            }
        } else {
            System.out.println("DEBUG: Authentication.isAuthenticated() is FALSE. Not truly authenticated.");
            isTrulyAuthenticated = false;
        }


        if (isTrulyAuthenticated) {
            System.out.println("DEBUG: User is determined to be TRULY authenticated.");
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", authentication.getName()); // Use authentication.getName() for userId

            if (principal instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) principal;
                userInfo.put("roles", customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
                userInfo.put("userRole", customUserDetails.getUserRole());
                userInfo.put("distCode", customUserDetails.getDistCode());
//                System.out.println("DEBUG: Returning CustomUserDetails info: " + userInfo);
            } else if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                userInfo.put("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
                // For generic UserDetails, userRole and distCode are not available
                userInfo.put("userRole", null);
                userInfo.put("distCode", null);
//                System.out.println("DEBUG: Returning generic UserDetails info: " + userInfo);
            } else {
                // If principal is not UserDetails, just return basic info
                userInfo.put("roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
                userInfo.put("userRole", null);
                userInfo.put("distCode", null);
//                System.out.println("DEBUG: Returning basic info for non-UserDetails principal: " + userInfo);
            }
//            System.out.println("--- DEBUG: Backend /user-info finished (200 OK) ---");
            return ResponseEntity.ok(userInfo);
        } else {
//            System.out.println("DEBUG: User is NOT truly authenticated. Returning 401.");
//            System.out.println("--- DEBUG: Backend /user-info finished (401 Unauthorized) ---");
            return ResponseEntity.status(401).body("Not authenticated or anonymous user.");
        }
    }
}
// 2