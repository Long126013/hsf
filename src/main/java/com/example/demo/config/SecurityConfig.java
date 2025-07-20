package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService; // <--- ADD THIS IMPORT
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // <--- ADD THIS IMPORT
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // <--- ADD THIS IMPORT
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService; // <--- ADD THIS FIELD

    // <--- ADD THIS CONSTRUCTOR
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // <--- CHANGE: Allow access to /login and static resources
                        .requestMatchers("/login", "/register","/css/**", "/js/**", "/images/**").permitAll()
                        // <--- CHANGE: All other requests require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")             // <--- CHANGE: Specify your custom login page GET URL
                        .loginProcessingUrl("/login")    // <--- CHANGE: Spring Security handles POST to this URL
                        .defaultSuccessUrl("/products", true) // <--- CHANGE: Redirect after successful login
                        .failureUrl("/login?error")      // <--- CHANGE: Redirect on failed login
                        .permitAll()                     // <--- CHANGE: Allow access to login process
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")            // <--- ADD: URL to trigger logout
                        .logoutSuccessUrl("/login?logout") // <--- ADD: Redirect after logout
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable); // <--- IMPORTANT: Temporarily disable CSRF for testing. REMOVE IN PRODUCTION!

        return http.build();
    }

    // <--- ADD THIS BEAN: Defines the BCrypt password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // <--- ADD THIS BEAN: Configures the authentication provider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService); // Use your custom service
        provider.setPasswordEncoder(passwordEncoder());           // Use your BCrypt encoder
        return provider;
    }
}