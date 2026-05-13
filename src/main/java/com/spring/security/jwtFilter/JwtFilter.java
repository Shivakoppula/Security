package com.spring.security.jwtFilter;

import java.io.IOException;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.service.JWTService;
import com.spring.security.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//filters the password and token whether which one passes through tokens
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		
		//storing token and verifying token is null or not
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			token=authHeader.substring(7);
			username=jwtService.extractUserName(token);
		}
		// verifying username and validating token authetication
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
			
			
			//to avoid redendancy we used application context
			UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
			if(jwtService.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
