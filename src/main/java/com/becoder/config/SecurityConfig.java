package com.becoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	public CustomAuthSucessHandler sucessHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public UserDetailsService getDetailsService()
	{
		return new CustomUserDetailsService();
	}
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider()
	{
		
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		//without ROle base authentication
//		http.csrf().disable()
//		.authorizeHttpRequests().requestMatchers("/","/register","/signin").permitAll()
//		.requestMatchers("/user/**").authenticated().and()
//		.formLogin().loginPage("/signin").loginProcessingUrl("/login")
//		.defaultSuccessUrl("/user/profile").permitAll();
//		
		
		
		//Role base Authentication
		http.csrf().disable()
		.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/**").permitAll().and()
		.formLogin().loginPage("/signin").loginProcessingUrl("/login")
		.successHandler(sucessHandler)
		.permitAll();
		
		return http.build();
	}
	
	
}
