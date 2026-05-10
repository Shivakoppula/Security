package com.spring.security.configuration;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.security.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http.csrf(customizer->customizer.disable());// disable security
		http.authorizeHttpRequests(request->request.anyRequest().authenticated()); //authenticate any request from different site
	   //http.formLogin(Customizer.withDefaults()); //customizer username passowrd
	   http.httpBasic(Customizer.withDefaults());// to avoid html form login
	   http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
	
	//username and password from database
	
	@Bean
	public AuthenticationProvider authenticate() {
		//DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		//provider.setUserDetailsPasswordService((UserDetailsPasswordService) userDetailsService);
		return provider;
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
