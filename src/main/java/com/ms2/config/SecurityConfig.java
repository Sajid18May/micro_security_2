package com.ms2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ms2.filter.JwtFilter;
import com.ms2.service.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private LoginService loginService;
	private JwtFilter jwtFilter;
	public SecurityConfig(LoginService loginService,JwtFilter jwtFilter) {
		this.loginService= loginService;
		this.jwtFilter= jwtFilter;
	}
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(req -> {
            req.requestMatchers(
                    "/sm/v1/register",
                    "/sm/v1/login",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**")
                    .permitAll()
                    .requestMatchers(
                            "/sm/v1/admin/welcome"
                    		)
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated();
        }).authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(loginService);
		authenticationProvider.setPasswordEncoder(getEncoder());
		
		return authenticationProvider;
	}
}
