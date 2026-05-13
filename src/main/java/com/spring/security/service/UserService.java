package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.entities.Users;
import com.spring.security.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	//becrypt need to create object for BCryptPasswordEncoder pass value for strength
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	//for verifing create authenticationmanager
	@Autowired
	AuthenticationManager authManager;
	
	//for generating   jwt tokens
	@Autowired
	private JWTService jwtService;
	

	public Users register(Users users) {
		
		
		//for becrypting password
		users.setPassword(encoder.encode(users.getPassword()));
	  return repo.save(users);
	
	}

	public String verify(Users user) {
		Authentication authenticate=         // by using this UsernamePasswordAuthenticationToken filter to acheive token generation
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		if(authenticate.isAuthenticated())// is used to authenticated or not and boolean type
		{                      //if  userverified generating token
			return jwtService.generateToken(user.getUsername());
		}
		return "fail";
	}

}
