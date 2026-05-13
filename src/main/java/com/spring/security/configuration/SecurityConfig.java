package com.spring.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.security.jwtFilter.JwtFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http.csrf(customizer->customizer.disable())// disable security
		.authorizeHttpRequests(request->request
				//for skipping security for specific urls like register and login
				.requestMatchers("/register","/login")
				.permitAll()
				.anyRequest().authenticated()) //authenticate any request from different site
		        //.formLogin(form->form.disable()) // for disabling login of jwt login form
		        //.httpBasic(https->https.disable())
	   //http.formLogin(Customizer.withDefaults()); //customizer username passowrd
	   .httpBasic(Customizer.withDefaults())// to avoid html form login
	   .sessionManagement(session->session
			   .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //instead of passing username and password we are passing token
		
		return http.build();
	}
	
	//username and password from database
	
	@Bean
	public AuthenticationProvider authenticate() {
		//DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());// for this while login password not decoding bcz nopasswordencoder()
		//for encoding we use object creation for BCryptingoasswordEncoder()
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // 12 strength
		//provider.setUserDetailsPasswordService((UserDetailsPasswordService) userDetailsService);
		return provider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	
	
	//custom username and password with role for only defaultly
//	@Bean
//	public UserDetailsService userDetail() {
//		UserDetails user1=User
//				         .withDefaultPasswordEncoder()
//				         .username("kiran")
//				         .password("kiran@123")
//				         .roles("user")
//				         .build();
//		UserDetails user2=User
//		         .withDefaultPasswordEncoder()
//		         .username("shiva")
//		         .password("shiva@1234")
//		         .roles("Admin")
//		         .build();
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
}
