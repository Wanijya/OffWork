package com.example.pds.config;

import com.example.pds.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer; // Import Customizer for modern config
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; // Note: This is deprecated in Spring Security 6+
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter is deprecated in Spring Security 6+

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF using the modern lambda syntax
                .csrf(csrf -> csrf.disable())
                // Configure CORS using the modern lambda syntax with default settings
                .cors(Customizer.withDefaults())
                // Configure authorization requests
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/api/dashboard/login").permitAll() // Login endpoint is public
                                .antMatchers("/api/dashboard/logout").permitAll() // Logout endpoint is public
                                .antMatchers("/api/dashboard/user-info").authenticated() // User info requires authentication
                                .antMatchers("/api/dashboard/metrics").authenticated() // Metrics require authentication
                                .antMatchers("/api/dashboard/details/**").authenticated() // Details require authentication
                                .anyRequest().authenticated() // All other requests require authentication
                )
                // Configure form login
                .formLogin(formLogin ->
                        formLogin
                                .loginProcessingUrl("/api/dashboard/login")
                                .usernameParameter("userId")
                                .passwordParameter("password")
                                .successHandler((request, response, authentication) -> {
                                    response.setStatus(200);
                                    response.getWriter().write("Login successful");
                                    response.getWriter().flush();
                                })
                                .failureHandler((request, response, exception) -> {
                                    response.setStatus(401);
                                    response.getWriter().write("Login failed: " + exception.getMessage());
                                    response.getWriter().flush();
                                })
                                .permitAll() // Allow all to access the login form (though processing is handled above)
                )
                // Configure logout
                .logout(logout ->
                        logout
                                .logoutUrl("/api/dashboard/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    response.setStatus(200);
                                    response.getWriter().write("Logout successful");
                                    response.getWriter().flush();
                                })
                )
                // Configure session management
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                                .expiredSessionStrategy(sessionInformationExpiredStrategy())
                )
                // Configure exception handling (this is now a direct call on http)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(unauthorizedEntryPoint()) // Use our custom entry point
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("SHA-256");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/api/dashboard/login?expired");
    }

    // Custom AuthenticationEntryPoint to return 401 Unauthorized for unauthenticated requests
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }
}
//2