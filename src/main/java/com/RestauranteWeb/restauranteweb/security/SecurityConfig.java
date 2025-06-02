package com.RestauranteWeb.restauranteweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final CustomUserDetailsService userDetailsService;
        public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
            }
         @Bean
         public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/register", "/css/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true") // URL personalizada para errores
                .permitAll()
            )
            .logout((logout) -> logout.permitAll())
            .userDetailsService(userDetailsService); // Añade esto
            
         return http.build();
          }
    
         @Bean
        public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }
    
}
