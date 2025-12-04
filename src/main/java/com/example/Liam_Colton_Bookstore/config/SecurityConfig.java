// i have this here to disable the spring security thing so i dont have to enter the user and password when i relaunch the spring server

package com.example.Liam_Colton_Bookstore.config;
import com.example.Liam_Colton_Bookstore.repository.UserRepo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // allow everything
        return http.build();
    }

    @Bean // this is the password hashing part
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider() {
            @Override
            public void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
                if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
                    throw new BadCredentialsException("Bad credentials");
                }
            }
        };
        authProvider.setUserDetailsService(email -> userRepository.findByEmail(email)
                .map(u -> org.springframework.security.core.userdetails.User
                        .withUsername(u.getEmail())
                        .password(u.getPassword())
                        .roles("USER") // or whatever roles you want
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}


